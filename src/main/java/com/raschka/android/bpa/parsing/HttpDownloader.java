package com.raschka.android.bpa.parsing;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.raschka.android.bpa.Postleitzahl;
import com.raschka.android.bpa.domain.BenzinSorte;

import javax.inject.Inject;
import java.io.IOException;

public class HttpDownloader implements Downloader {

    @Inject
    private RequestFactoryProvider requestFactoryProvider;

    private static final String URL = "http://auto.freenet.de/freenet/servlet/external?url=http%3A%2F%2Fnn3.freenet.de%2Fclevertanken%2Fmain.php%3Fcm%3D1%26sprit%3D";

    public String download(BenzinSorte sorte, Postleitzahl postleitzahl) {
        HttpRequestFactory httpRequestFactory = requestFactoryProvider.createRequestFactory();
        HttpRequest request;
        StringBuilder urlBuilder = new StringBuilder(URL);
        urlBuilder.append(sorte.name()).append("%26ort%3D").append(postleitzahl.plz());
        try {
            request = httpRequestFactory
                    .buildGetRequest(new GenericUrl(urlBuilder.toString()));
            return request.execute().parseAsString();
        } catch (IOException e) {
            throw new IllegalArgumentException("plz or sorte not found",e);
        }
    }
}
