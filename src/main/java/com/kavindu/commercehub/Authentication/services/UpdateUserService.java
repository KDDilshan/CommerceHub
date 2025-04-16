package com.kavindu.commercehub.Authentication.services;

import com.kavindu.commercehub.Authentication.Dto.Request.RegisterDto;
import com.kavindu.commercehub.Authentication.Dto.Request.UserDto;
import com.kavindu.commercehub.Authentication.Dto.Request.UserUpdate;
import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Authentication.repositories.UserRepository;
import com.kavindu.commercehub.Exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserService {

    private final static Logger logger= LoggerFactory.getLogger(UpdateUserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public  ResponseEntity<UserDto> update(UserUpdate userUpdate) {
        Integer userID=userUpdate.getId();
        logger.info("update for : {}",userID);

        AppUser user= userRepository.findById(userID).get();
        if(user==null){
            throw new UserNotFoundException("User with id [%s] not found".formatted(userID));
        }
        logger.info("updateing user [%s]".formatted(user));

        user.setUsername(userUpdate.getAppUser().getUsername());
        user.setEmail(userUpdate.getAppUser().getEmail());
        user.setPassword(passwordEncoder.encode(userUpdate.getAppUser().getPassword()));

        logger.info("updated user : {}",user);
        userRepository.save(user);
        return ResponseEntity.ok(new UserDto(user));

    }
}
