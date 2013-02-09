package com.raschka.android.bpa.domain;

import android.location.Location;

import java.util.List;

public interface TankstellenFinder {
    List<Tankstelle> findeTankstellen(Location location);
}
