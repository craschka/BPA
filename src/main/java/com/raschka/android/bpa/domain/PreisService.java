package com.raschka.android.bpa.domain;

import java.util.List;

public interface PreisService {
    Preis readPreis(Tankstelle tankstelle, BenzinSorte benzinSorte);

    List<Preis> readPreise(List<Tankstelle> tankstellen, BenzinSorte benzinSorte);
}
