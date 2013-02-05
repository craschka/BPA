package com.raschka.android.bpa.domain;

import android.location.Location;
import com.raschka.android.bpa.Postleitzahl;

public interface LocationFinder {
    Postleitzahl findLastKnownPostleitzahl();

    Location findLastKnownPosition();
}
