package com.raschka.android.bpa;

import com.google.common.io.Files;
import com.raschka.android.bpa.parsing.JSoupHtmlParser;
import com.raschka.android.bpa.parsing.TankstellenEntry;
import junit.framework.TestCase;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

public class JSoupHtmlParser2Test extends TestCase {
    private String htmlFile;
    private JSoupHtmlParser htmlParser;
    private Element htmlEntry;

    @Override
    public void setUp() throws IOException {
        htmlFile = Files.toString(new File(getClass().getResource("./TSP2.html.part").getPath()), Charset.forName("UTF-8"));
        htmlParser = new JSoupHtmlParser();
        htmlEntry = htmlParser.findRows(htmlFile).get(2);
    }

    public void testParseEntryAsHTML() {
        Elements elements = htmlParser.findRows(htmlFile);
        assertTrue(elements.html().contains("colErg1"));
        assertTrue(elements.html().contains("colErg2"));
        assertTrue(elements.html().contains("colErg3"));
    }

    public void testParseName() {
        String name = htmlParser.parseName(htmlEntry.getAllElements());
        assertEquals("Shell", name);
    }

    public void testParseDateTime() {
        String date = htmlParser.parseDateTime(htmlEntry.getAllElements());
        assertEquals("12.02.2013 13:20:10", date);
    }

    public void testParsePrice() {
        BigDecimal preis = htmlParser.parsePreis(htmlEntry.getAllElements());
        assertTrue(new BigDecimal(1.599).subtract(preis).doubleValue() <= 0.1);
    }

    public void testParse() {
        List<TankstellenEntry> entries = htmlParser.parse(htmlFile, null);

        assertEquals(3, entries.size());
    }

    public void testParseStadt(){
        assertEquals("Düsseldorf",htmlParser.parseStadt(htmlEntry.getAllElements()));
    }

    public void testParseAdresse(){
        String adresse = htmlParser.parseAdresse(htmlEntry.getAllElements());

        assertEquals("Ronsdorfer Str. 95",adresse);
    }
}
