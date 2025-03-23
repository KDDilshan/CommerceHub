package com.kavindu.commercehub.ProfanityAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfanityResponse {
    private String profanity_name;
    private String censored_name;
    private Boolean has_Profanity;
}
