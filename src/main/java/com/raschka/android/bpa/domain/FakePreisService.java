package com.raschka.android.bpa.domain;

import android.location.Address;
import com.raschka.android.bpa.GeocoderProvider;
import com.raschka.android.bpa.Postleitzahl;
import com.raschka.android.bpa.parsing.Downloader;
import com.raschka.android.bpa.parsing.HtmlParser;
import com.raschka.android.bpa.parsing.TankstellenEntry;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FakePreisService implements PreisService {
    @Inject
    private HtmlParser htmlParser;

    @Inject
    private Downloader downloader;

    @Inject
    private GeocoderProvider geocoderProvider;

    public Preis readPreis(Tankstelle tankstelle, BenzinSorte benzinSorte) {

        String plz = null;
        try {
            List<Address> addresses = geocoderProvider.createGeocoder().getFromLocation(tankstelle.location().lat,tankstelle.location().lng,1);
            for (Address address : addresses) {
                plz = address.getPostalCode();
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<TankstellenEntry>entries =
                htmlParser.parse(downloader.download(benzinSorte, new Postleitzahl(Integer.valueOf(plz))));
        TankstellenEntry gefundeneTankstelle = null;
        for (TankstellenEntry entry : entries) {
            int levenshteinDistance = StringUtils.getLevenshteinDistance(lowFirstWord(entry.name()), lowFirstWord(tankstelle.name()));
            if (levenshteinDistance <= 2){
                gefundeneTankstelle = entry;
                break;
            }
        }
        int preis;
        if (gefundeneTankstelle == null){
            preis = 0;
        } else {
            preis = Integer.valueOf(gefundeneTankstelle.price().replaceAll("\\.", ""));
        }

        return new Preis(preis, new Date().getTime(), tankstelle, "keine Daten");
    }

    private String lowFirstWord(String name) {
        return name.substring(0,name.indexOf(" ")>0?name.indexOf(" "):name.length()).toLowerCase();
    }

    public List<Preis> readPreise(List<Tankstelle> tankstellen, BenzinSorte benzinSorte) {
        List<Preis> preise = new ArrayList<Preis>(tankstellen.size());
        for (Tankstelle tankstelle : tankstellen) {
            preise.add(readPreis(tankstelle,benzinSorte));
        }
        return preise;
    }
}
