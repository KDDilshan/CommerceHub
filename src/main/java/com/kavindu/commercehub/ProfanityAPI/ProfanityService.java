package com.kavindu.commercehub.ProfanityAPI;

import com.kavindu.commercehub.Product.service.repos.Querry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProfanityService {


     private final RestTemplate restTemplate;
     private static final String API_URL="https://api.api-ninjas.com/v1/profanityfilter?text=";
     private static final String API_KEY="k9PVUsdRYVG7TzZMUJkH+A==OiVvmnEbJdLNIsGw";

    public ProfanityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Boolean execute(String text) {
        String uel=API_URL+text;
        HttpHeaders headers=new HttpHeaders();
        headers.set("X-API-Key", API_KEY);
        HttpEntity<String> entity=new HttpEntity<>(headers);

        ResponseEntity<String> response=restTemplate.exchange(uel, HttpMethod.GET,entity,String.class);
        return response.getBody() != null && response.getBody().contains("true");

    }
}
