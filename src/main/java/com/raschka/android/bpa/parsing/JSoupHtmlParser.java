package com.raschka.android.bpa.parsing;

import com.raschka.android.bpa.domain.Tankstelle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JSoupHtmlParser implements HtmlParser {
    private int i;

    public List<TankstellenEntry> parse(String htmlDocument, Tankstelle tankstelle)
    {
        if (htmlDocument == null || htmlDocument.isEmpty())
            return new ArrayList<TankstellenEntry>(0);
        List<TankstellenEntry> entries = new ArrayList<TankstellenEntry>();
        for (Element element : findRows(htmlDocument)){
            if (i++<2){
                continue;
            }
            if (element.children().size()<=1){
                i=0;
                break;
            }
            entries.add(new TankstellenEntry(parsePreis(element.getAllElements()),parseName(element.getAllElements()),
                    parseDateTime(element.getAllElements()), parseAdresse(element.getAllElements())+", "+parseStadt(element.getAllElements()),tankstelle));
        }
        return entries;
    }

    public String parseAdresse(Elements elements) {
        Node node = elements.select("a").get(0).childNode(4);
        if (node.toString().startsWith("<br />")){
            return elements.select("a").get(0).childNode(3).toString().trim();
        }
        return node.toString().trim();
    }

    public Elements findRows(String htmlFile){
        Document document = Jsoup.parse(htmlFile);
        return document.select("tr");
    }

    public String parseName(Elements elements){
        return elements.select("strong").first().ownText();
    }

    public String parseDateTime(Elements elements){
        Element e = elements.select("td").last();
        String date = e.childNode(0).toString().trim();
        String time = e.childNode(2).toString().trim();
        return date + " " + time;
    }

    public BigDecimal parsePreis(Elements elements){
        String innerText = elements.select("strong").last().ownText();
        String preis = innerText.replaceAll(" Euro","");//.replaceAll("\\.",",");
        return new BigDecimal(preis);
    }

    public String parseStadt(Elements allElements) {
        String ortMitPLZ = allElements.select("a").get(0).childNode(5).toString()
                .replace("&auml;","ä").replace("&Auml;","Ä")
                .replace("&ouml;","ö").replace("&Ouml;","Ö")
                .replace("&uuml;","ü").replace("&Uuml;","Ü")
                .replace("&szlig;","ß");;
        return ortMitPLZ.substring(ortMitPLZ.lastIndexOf(' ')).trim().toString();
    }
}
