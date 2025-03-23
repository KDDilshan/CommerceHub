//package com.kavindu.commercehub.ProfanityAPI;
//
//import com.kavindu.commercehub.Product.service.repos.Querry;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class ProfanityService implements Querry<String, ProfanityResponse> {
//
//    @Autowired
//     private final RestTemplate restTemplate;
//
//    public ProfanityService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Override
//    public ResponseEntity<ProfanityResponse> execute(String text) {
//        ProfanityResponse profanityResponse = restTemplate.getForObject("https://api.api-ninjas.com/v1/profanityfilter?text=",ProfanityResponse.class);
//        return ResponseEntity.ok(profanityResponse);
//    }
//}
