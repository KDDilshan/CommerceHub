package com.kavindu.commercehub.Authentication.config; // adjust the package name as needed

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

@OpenAPIDefinition(
        info = @Info(
                title = "CommerceHub API",
                version = "1.0.0",
                description = "This is the API documentation for CommerceHub",
                contact = @Contact(
                        name = "Kavindu",
                        email = "kavindu.dilshan.dev@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
