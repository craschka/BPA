package com.raschka.android.bpa.parsing;

import java.util.List;

public interface HtmlParser {
    List<TankstellenEntry> parse(String htmlDocument);
}
