package com.raschka.android.bpa;

import com.google.inject.AbstractModule;
import com.raschka.android.bpa.domain.*;
import com.raschka.android.bpa.parsing.*;

public class BPAModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PreisService.class).to(ManuellGemeldeterPreisService.class);
        bind(LocationFinder.class).to(BestProviderLocationFinder.class);
        //bind(Downloader.class).to(FakeDownloader.class);
        bind(Downloader.class).to(HttpDownloader.class);
        bind(HtmlParser.class).to(JSoupHtmlParser.class);
        bind(TankstellenFinder.class).to(GooglePlacesTankstellenFinder.class);
    }

}