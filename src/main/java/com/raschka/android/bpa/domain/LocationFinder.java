package com.raschka.android.bpa.domain;

import com.raschka.android.bpa.Postleitzahl;

public interface LocationFinder {
    Postleitzahl findLastKnownPostleitzahl();
}
