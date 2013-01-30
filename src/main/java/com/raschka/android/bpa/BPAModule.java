package com.raschka.android.bpa;

import com.google.inject.AbstractModule;
import com.raschka.android.bpa.domain.BestProviderLocationFinder;
import com.raschka.android.bpa.domain.FakePreisService;
import com.raschka.android.bpa.domain.LocationFinder;
import com.raschka.android.bpa.domain.PreisService;

public class BPAModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ITest.class).to(Test.class);
        bind(PreisService.class).to(FakePreisService.class);
        bind(LocationFinder.class).to(BestProviderLocationFinder.class);
    }

}