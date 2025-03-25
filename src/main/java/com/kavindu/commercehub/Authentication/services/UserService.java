package com.kavindu.commercehub.Authentication.services;

import com.kavindu.commercehub.Authentication.Dto.Request.LoginDto;
import com.kavindu.commercehub.Authentication.Dto.Request.RegisterDto;
import com.kavindu.commercehub.Authentication.Dto.Response.AuthResponse;
import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Authentication.models.RefreshToken;
import com.kavindu.commercehub.Authentication.models.Roles;
import com.kavindu.commercehub.Authentication.repositories.RefreshRepository;
import com.kavindu.commercehub.Authentication.repositories.RoleRepository;
import com.kavindu.commercehub.Authentication.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    private final static Logger logger= LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final RefreshRepository refreshRepository;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RoleRepository roleRepository, JwtService jwtService, RefreshRepository refreshRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.refreshRepository = refreshRepository;
    }

    public AppUser RegisterUser(RegisterDto registerDto) {
        Roles role=roleRepository.findByName("ROLE_USER").orElseThrow(()->new RuntimeException("Role Not Found"));
        logger.info("role",role.getName());
        AppUser user=new AppUser();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    public AppUser RegisterAdmin(RegisterDto registerDto) {
        Roles role=roleRepository.findByName("ROLE_ADMIN").orElseThrow(()->new RuntimeException("Role Not Found"));
        AppUser user=new AppUser();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    public AuthResponse loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        AppUser user = (AppUser) authentication.getPrincipal();

        logger.info("user: "+user.getUsername());
        String Token=jwtService.generateToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);

        RefreshToken exsitingRefreshToken=refreshRepository.findByUser(user);
        if(exsitingRefreshToken !=null){
            exsitingRefreshToken.setRefreshToken(refreshToken);
            exsitingRefreshToken.setExpiresIn(System.currentTimeMillis()+7*24*60*60*1000);
            refreshRepository.save(exsitingRefreshToken);
        }else{
            RefreshToken rToken=new RefreshToken();
            rToken.setRefreshToken(refreshToken);
            rToken.setUser(user);
            rToken.setExpiresIn(System.currentTimeMillis() + 7*24*60*60*1000);
            refreshRepository.save(rToken);
        }
        return new AuthResponse(Token,refreshToken, user.getUsername(), user.getRoles());

    }
    public Map<String, String> getAccessToken(String refreshToken) {
        RefreshToken refreshToken1=refreshRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not present"));

        if(refreshToken1.getExpiresIn()<System.currentTimeMillis()){
            throw  new RuntimeException("Refresh token expired");
        }
        AppUser user=refreshToken1.getUser();
        String accessToken=jwtService.generateToken(user);

        Map<String,String> tokens=new HashMap<>();
        tokens.put("access_token",accessToken);
        tokens.put("refresh_token",refreshToken);
        return tokens;
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            System.out.println("User is not authenticated.");
            return;
        }

        String email = authentication.getName();
        System.out.println("Logging out user: " + email);

        // Find the user by email
        AppUser user = userRepository.findByEmail(email)
                .orElse(null);

        if (user != null) {
            // Find and delete refresh token
            RefreshToken refreshToken = refreshRepository.findByUser(user);


            if (refreshToken != null) {
                refreshRepository.delete(refreshToken);
                System.out.println("Deleted refresh token for user: " + email);
            }
        }

        // Invalidate HTTP session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Clear security context
        SecurityContextHolder.clearContext();
    }


}
