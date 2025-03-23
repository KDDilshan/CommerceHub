package com.kavindu.commercehub.ProfanityAPI;

import com.kavindu.commercehub.Product.models.Product;
import lombok.experimental.PackagePrivate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfanityContoller {

    private final ProfanityService profanityService;

    public ProfanityContoller(ProfanityService profanityService) {
        this.profanityService = profanityService;
    }

    @GetMapping("/cheak-profinity")
    public ResponseEntity<String> cheakProfinity(@RequestParam String text) {
        Boolean hasProfinity=profanityService.execute(text);
        if(hasProfinity){
            return ResponseEntity.badRequest().body("Profinity dected");
        }else {
            return ResponseEntity.ok().body("Profinity not found");
        }
    }
}
