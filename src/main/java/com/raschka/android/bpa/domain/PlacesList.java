package com.raschka.android.bpa.domain;

import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlacesList implements Serializable {
    @Key
    public String status;

    @Key
    public List<Place> results = new ArrayList<Place>();

    public List<Tankstelle> tankstellen(){
        List<Tankstelle> tankstellen = new ArrayList<Tankstelle>();
        for (Place result : results) {
            tankstellen.add(result.tankstelle());
        }
        return tankstellen;
    }
}
