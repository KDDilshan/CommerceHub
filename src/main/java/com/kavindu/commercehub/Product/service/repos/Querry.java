package com.kavindu.commercehub.Product.service.repos;

import org.springframework.http.ResponseEntity;

public interface Querry<Input,OutPut>{
    ResponseEntity<?> execute(Input input);
}
