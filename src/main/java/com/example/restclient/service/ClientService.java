package com.example.restclient.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientService {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static String url = "http://94.198.50.185:7081/api/users";
    private static String cookie = "";
    private static HttpHeaders headers = new HttpHeaders();

    @EventListener(ApplicationReadyEvent.class)
    public static void main(){
        System.out.println(getUsers().getBody());
        System.out.println(addUser().getBody() + changeUser().getBody() + deleteUser().getBody());
    }

    public static ResponseEntity<String> getUsers() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        cookie = responseEntity.getHeaders().getFirst("Set-Cookie");
        headers.set("Cookie", cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return responseEntity;
    }

    static ResponseEntity<String> addUser() {
        String body = "{\"id\":3,\"name\":\"James\",\"lastName\":\"Brown\",\"age\":21}";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, entity, String.class);
    }

    static ResponseEntity<String> changeUser() {
        String body = "{\"id\":3,\"name\":\"Thomas\",\"lastName\":\"Shelby\",\"age\":21}";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    static ResponseEntity<String> deleteUser() {
        String body = "{\"id\":3,\"name\":\"Thomas\",\"lastName\":\"Shelby\",\"age\":21}";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(url + "/3", HttpMethod.DELETE, entity, String.class);
    }
}
