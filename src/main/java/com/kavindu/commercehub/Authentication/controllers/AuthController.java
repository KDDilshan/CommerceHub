package com.kavindu.commercehub.Authentication.controllers;

import com.kavindu.commercehub.Authentication.Dto.Request.LoginDto;
import com.kavindu.commercehub.Authentication.Dto.Request.RegisterDto;
import com.kavindu.commercehub.Authentication.Dto.Request.TokenRequest;
import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Authentication.services.JwtService;
import com.kavindu.commercehub.Authentication.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/api/")
public class AuthController {
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody RegisterDto registerDto) {
        try{
            AppUser registeredUser=userService.RegisterUser(registerDto);
            return ResponseEntity.ok(registeredUser);
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> LoginUser(@RequestBody LoginDto loginDto) {
        try{
            return ResponseEntity.ok(userService.loginUser(loginDto));
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/Admin_Register")
    public ResponseEntity<?> RegisterAdmin(@RequestBody RegisterDto registerDto) {
        try{
            AppUser registerdAdmin=userService.RegisterAdmin(registerDto);
            return ResponseEntity.ok(registerdAdmin);
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/Refresh")
    public ResponseEntity<?> getAcessToken(@RequestBody TokenRequest request) {
        try {
            String refreshToken=request.getRefresh_token();
            if(refreshToken==null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh token is null");
            }
            Map<String,String> tokens=userService.getAccessToken(refreshToken);
            return ResponseEntity.ok(tokens);
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/Logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try{
            userService.logout(request);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
