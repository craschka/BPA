package com.raschka.android.bpa;

import com.google.inject.AbstractModule;
import com.raschka.android.bpa.domain.*;
import com.raschka.android.bpa.parsing.Downloader;
import com.raschka.android.bpa.parsing.HtmlParser;
import com.raschka.android.bpa.parsing.HttpDownloader;
import com.raschka.android.bpa.parsing.JSoupHtmlParser;

public class BPAModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PreisService.class).to(FakePreisService.class);
        bind(LocationFinder.class).to(BestProviderLocationFinder.class);
        bind(Downloader.class).to(HttpDownloader.class);
        bind(HtmlParser.class).to(JSoupHtmlParser.class);
        bind(TankstellenFinder.class).to(GooglePlacesTankstellenFinder.class);
    }

}