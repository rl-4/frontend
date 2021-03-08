package services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.function.Supplier;

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
    public HttpResponse<String> uploadPost(URI uri, MultipartFile file) {

        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            Supplier<? extends InputStream> supplier = new Supplier<InputStream>() {
                @Override
                public InputStream get() {
                    try {
                        return file.getInputStream();
                    } catch (IOException e) {
                        return null;
                    }
                }
            };
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .POST(HttpRequest.BodyPublishers.ofInputStream(supplier))
                            .uri(uri)
                            .header("ContentType", "multipart/form-data")
                            .timeout(Duration.ofMinutes(1))
                            .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResponseEntity<String> upload(String url, MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file",new FileSystemResource(convert(file)));
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        String serverUrl = url;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.POST,requestEntity,String.class);
        return response;
    }
    public static File convert(MultipartFile file)
    {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return convFile;
    }
}