package com.raschka.android.bpa.domain;

import java.util.Date;

public class FakePreisService implements PreisService {
    public Preis readPreis(TankstellenId tankstellenId, BenzinSorte benzinSorte) {
        return new Preis((int)tankstellenId.id(), new Date().getTime());
    }
}
