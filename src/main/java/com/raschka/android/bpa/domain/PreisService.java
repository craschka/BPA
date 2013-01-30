package com.raschka.android.bpa.domain;

public interface PreisService {
    Preis readPreis(TankstellenId tankstellenId, BenzinSorte benzinSorte);
}
