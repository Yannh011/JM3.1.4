package com.example.javamentor;


import com.example.javamentor.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Application {

    static final String URL = "http://91.241.64.178:7081/api/users";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders myHeaders = new HttpHeaders();
    User user = new User(3L,"James","Brown", (byte) 25);

    public static void main(String[] args) {
        Application springRestClient = new Application();

        springRestClient.getEmployees();

        springRestClient.createEmployee();

        springRestClient.updateEmployee();

        springRestClient.deleteEmployee();
    }

    private void getEmployees() {

        ResponseEntity<String> result = restTemplate.getForEntity(URL, String.class);

        HttpHeaders headers = result.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        System.out.println(result);

        myHeaders = new HttpHeaders();

        myHeaders.set("Cookie", set_cookie);
        myHeaders.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(set_cookie);
    }

    private void createEmployee() {

        HttpEntity<User> requestBody = new HttpEntity<>(user, myHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestBody, String.class);

        ResponseEntity<String> result2 = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity<String>(myHeaders), String.class);

        System.out.println(responseEntity);
        System.out.println(result2);
    }

    private void updateEmployee() {

        user.setName("Thomas");
        user.setLastName("Shelby");

        HttpEntity<User> requestBody = new HttpEntity<>(user, myHeaders);

        ResponseEntity<String> userResponseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestBody, String.class);
        ResponseEntity<String> result3 = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity<String>(myHeaders), String.class);

        System.out.println(userResponseEntity);
        System.out.println(result3);
    }

    private void deleteEmployee() {

        HttpEntity<User> deleteBody = new HttpEntity<>(user, myHeaders);

        ResponseEntity<String> deleteEntity = restTemplate.exchange(URL+"/3", HttpMethod.DELETE, deleteBody, String.class);

        ResponseEntity<String> result4 = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity<String>(myHeaders), String.class);

        System.out.println(deleteEntity);
        System.out.println(result4);

    }
}
