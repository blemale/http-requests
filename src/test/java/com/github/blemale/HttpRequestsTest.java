package com.github.blemale;

import com.github.blemale.HttpRequests.Timeouts;
import org.apache.http.client.fluent.Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestsTest {

    @Test
    public void should_call_google_with_timeouts() throws Exception {
        Response response = HttpRequests.Get("http://www.google.com", Timeouts.NORMAL).execute();
        try (InputStream inputStream = response.returnContent().asStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines().forEach(System.out::println);
        }
    }

}