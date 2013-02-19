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

        String plz = extractPlz(tankstelle);

        List<TankstellenEntry>entries =
                htmlParser.parse(downloader.download(benzinSorte, new Postleitzahl(Integer.valueOf(plz))), null);
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

    private String extractPlz(Tankstelle tankstelle) {
        String plz = null;
        try {
            List<Address> addresses = geocoderProvider.createGeocoder().getFromLocation(tankstelle.location().lat,tankstelle.location().lng,1);
            for (Address address : addresses) {
                plz = address.getPostalCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plz;
    }

    private String lowFirstWord(String name) {
        return name.substring(0,name.indexOf(" ")>0?name.indexOf(" "):name.length()).toLowerCase();
    }

    public List<Preis> readPreise(List<Tankstelle> tankstellen, BenzinSorte benzinSorte) {
        Postleitzahl plz = extractPlz(tankstellen);

        String htmlDocument = downloader.download(benzinSorte, plz);
        List<TankstellenEntry>entries = htmlParser.parse(htmlDocument, null);

        List<TankstellenEntry> gefundeneTankstellen = new ArrayList<TankstellenEntry>();
        for (TankstellenEntry entry : entries) {
            for (Tankstelle tankstelle : tankstellen) {
                int levenshteinDistance = StringUtils.getLevenshteinDistance(entry.adresse(), tankstelle.adresse());
                if (levenshteinDistance <= 5){
                    gefundeneTankstellen.add(entry);
                    entry.setTankstelle(tankstelle);
                    break;
                }
            }
        }

        List<Preis> preise = new ArrayList<Preis>(tankstellen.size());
        for (TankstellenEntry tankstellenEntry : gefundeneTankstellen) {
            preise.add(new Preis(Integer.valueOf(tankstellenEntry.price().replaceAll("\\.", "")), new Date().getTime(), tankstellenEntry.tankstelle(), "keine Daten"));
        }
        return preise;
    }

    private Postleitzahl extractPlz(List<Tankstelle> tankstellen) {
        if (tankstellen.size()==0){
            return new Postleitzahl(0);
        }
        return new Postleitzahl(Integer.valueOf(extractPlz(tankstellen.get(0))));
    }
}
