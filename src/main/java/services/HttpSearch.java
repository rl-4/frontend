package services;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class HttpSearch {
    public HttpResponse<String> searchGet(URI uri) {

        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .GET()
                            .uri(uri)
                            .timeout(Duration.ofMinutes(1))
                            .header("Accept", "application/json")
                            .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpResponse<String> searchPost(URI uri, String postBody) {

        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .POST(HttpRequest.BodyPublishers.ofString(postBody))
                            .uri(uri)
                            .timeout(Duration.ofMinutes(1))
                            .header("Accept", "application/json")
                            .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}