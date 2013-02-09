package com.raschka.android.bpa.parsing;

import com.raschka.android.bpa.Postleitzahl;
import com.raschka.android.bpa.domain.BenzinSorte;

public interface Downloader {
    String download(BenzinSorte sorte, Postleitzahl postleitzahl);
}
