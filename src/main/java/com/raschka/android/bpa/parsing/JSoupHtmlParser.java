package com.raschka.android.bpa.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JSoupHtmlParser implements HtmlParser {
    private int i;

    public List<TankstellenEntry> parse(String htmlDocument)
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
                    parseDateTime(element.getAllElements()), parseAdresse(element.getAllElements())));
        }
        return entries;
    }

    private String parseAdresse(Elements allElements) {
        return "";  //To change body of created methods use File | Settings | File Templates.
    }

    public Elements findRows(String htmlFile){
        Document document = Jsoup.parse(htmlFile);
        return document.select("tr");
    }

    String parseName(Elements elements){
        return elements.select("strong").first().ownText();
    }

    String parseDateTime(Elements elements){
        return elements.select("td").last().ownText();
    }

    BigDecimal parsePreis(Elements elements){
        String innerText = elements.select("strong").last().ownText();
        String preis = innerText.replaceAll(" Euro","");//.replaceAll("\\.",",");
        return new BigDecimal(preis);
    }

}
