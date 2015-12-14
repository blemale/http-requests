package com.github.blemale;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.apache.http.client.config.RequestConfig.DEFAULT;

public class HttpClientTest {

    @Test
    public void should_call_google() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet("http://www.google.com");
            try (CloseableHttpResponse response = client.execute(get)) {
                HttpEntity entity = response.getEntity();
                try (InputStream inputStream = entity.getContent();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    reader.lines().forEach(System.out::println);
                }
            }
        }
    }

    @Test
    public void should_call_google_with_timeouts() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet("http://www.google.com");
            RequestConfig config = RequestConfig.copy(DEFAULT)
                    .setConnectTimeout(10_000)
                    .setSocketTimeout(10_000)
                    .build();
            get.setConfig(config);
            try (CloseableHttpResponse response = client.execute(get)) {
                HttpEntity entity = response.getEntity();
                try (InputStream inputStream = entity.getContent();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    reader.lines().forEach(System.out::println);
                }
            }
        }
    }
}
