package com.github.blemale;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FluentHttpClientTest {

    @Test
    public void should_call_google_and_consume_input_stream() throws Exception {
        Response response = Request.Get("http://www.google.com").execute();
        try (InputStream inputStream = response.returnContent().asStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void should_call_google_and_consume_input_stream_with_timeout() throws Exception {
        Response response = Request.Get("http://www.google.com")
                .connectTimeout(10_000)
                .socketTimeout(10_000)
                .execute();
        try (InputStream inputStream = response.returnContent().asStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines().forEach(System.out::println);
        }
    }

    @Test
    public void should_call_google_and_consume_string() throws Exception {
        Response response = Request.Get("http://www.google.com").execute();
        String content = response.returnContent().asString();
        System.out.println(content);
    }
}
