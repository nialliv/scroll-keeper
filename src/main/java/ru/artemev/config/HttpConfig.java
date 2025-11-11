package ru.artemev.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

public class HttpConfig {
    private HttpConfig() {
    }

    private static final CloseableHttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

    public static CloseableHttpClient getHttpClient() {
        return HTTP_CLIENT;
    }
}
