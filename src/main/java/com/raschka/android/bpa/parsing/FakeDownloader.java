package com.raschka.android.bpa.parsing;

import android.content.Context;
import com.example.R;
import com.raschka.android.bpa.Postleitzahl;
import com.raschka.android.bpa.domain.BenzinSorte;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import java.io.IOException;

public class FakeDownloader implements Downloader {

    @Inject
    private Context context;

    public String download(BenzinSorte sorte, Postleitzahl postleitzahl) {
        try {
            return IOUtils.toString(context.getResources().openRawResource(R.raw.tsp2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
