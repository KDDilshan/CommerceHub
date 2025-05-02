package com.kavindu.commercehub.ProfanityAPI;

import com.kavindu.commercehub.Product.models.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.experimental.PackagePrivate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfanityContoller {

    private final ProfanityService profanityService;

    public ProfanityContoller(ProfanityService profanityService) {
        this.profanityService = profanityService;
    }

    @PostMapping("/cheak-profinity")
    @Operation(
            summary = "Check text for profanity",
            description = "Checks the provided text for any profane words and returns whether profanity was detected."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No profanity found in text"),
            @ApiResponse(responseCode = "400", description = "Profanity detected in text")
    })
    public ResponseEntity<String> cheakProfinity(@Parameter(description = "Text to check for profanity", required = true) @RequestParam String text) {
        Boolean hasProfinity=profanityService.execute(text);
        if(hasProfinity){
            return ResponseEntity.badRequest().body("Profinity dected");
        }else {
            return ResponseEntity.ok().body("Profinity not found");
        }
    }
}
