package com.github.gonzalezjo.githubtoemail.grabber;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;

public class EmailGrabber {
    private static final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();
    private static final String API_BASE = "https://api.github.com";

    private static void printEmailIn(HttpResponse response) throws IOException {
        final String body = new BasicResponseHandler().handleResponse(response);

        Arrays.stream(body.split(","))
                .filter(s -> s.contains("\"email\":"))
                .forEach(System.out::println);
    }


    public static void printEmailsOfUser(String username) throws IOException {
        final String USER_BASE = API_BASE + "/users/" + username;
        final HttpResponse response;

        printEmailIn(HTTP_CLIENT.execute(new HttpGet(USER_BASE)));
        printEmailIn(HTTP_CLIENT.execute(new HttpGet(USER_BASE + "/events")));

        response = HTTP_CLIENT.execute(new HttpGet(USER_BASE + "/repos?type=owner"));

        Arrays.stream(new BasicResponseHandler().handleResponse(response)
                .split("},\\{"))
                .filter(s -> s.contains("fork\":false"))
                .flatMap(s -> Arrays.stream(s.split(",")))
                .filter(s -> s.contains("full_name"))
                .map(s -> s.replaceAll("(.*/)", ""))
                .map(s -> s.replaceAll("\"", ""))
                .forEach(repository -> {
                    try {
                        printEmailIn(HTTP_CLIENT.execute(new HttpGet(
                                String.format("%s/repos/%s/%s/commits",
                                        API_BASE,
                                        username,
                                        repository)
                        )));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }
}
