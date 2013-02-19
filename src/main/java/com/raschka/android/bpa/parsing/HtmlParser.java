package com.raschka.android.bpa.parsing;

import com.raschka.android.bpa.domain.Tankstelle;

import java.util.List;

public interface HtmlParser {
    List<TankstellenEntry> parse(String htmlDocument, Tankstelle tankstelle);
}
