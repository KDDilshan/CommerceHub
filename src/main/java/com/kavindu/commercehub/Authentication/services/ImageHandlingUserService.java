package com.kavindu.commercehub.Authentication.services;

import com.kavindu.commercehub.Authentication.Dto.Request.RegisterDto;
import com.kavindu.commercehub.Authentication.Dto.Request.UserDto;
import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Authentication.repositories.UserRepository;
import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.S3Bucket.S3Buckets;
import com.kavindu.commercehub.S3Bucket.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageHandlingUserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final static Logger logger= LoggerFactory.getLogger(ImageHandlingUserService.class);

    public ImageHandlingUserService(UserRepository userRepository, S3Service s3Service, S3Buckets s3Buckets) {
        this.userRepository = userRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    public void uploadProductImage(Integer userId, MultipartFile file) {
        checkIfUserExists(userId);

        String imageName=UUID.randomUUID().toString();

        try{
            s3Service.putObject(
                    s3Buckets.getCustomer(),
                    "user-images/%s/%s".formatted(userId,imageName),
                    file.getBytes()
            );
        }catch (IOException e){
            throw  new RuntimeException("Filed tro upload user image: ",e);
        }

        AppUser user=userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        user.setImageName(imageName);
        userRepository.save(user);
    }

    public byte[] getUserImage(Integer userId) {
        var user= userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("user with id [%d] is not found".formatted(userId)));

        String imageName = user.getImageName();
        if (imageName == null || imageName.isBlank()) {
            throw new ProductNotFoundException(
                    "user with id [%s] profile image not found".formatted(userId)
            );
        }

        String key = "user-images/%s/%s".formatted(userId ,imageName);
        return s3Service.getObject(s3Buckets.getCustomer(), key);
    }

    private ResponseEntity<UserDto> checkIfUserExists(Integer id) {
        Optional<AppUser> user=userRepository.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(new UserDto(user.get()));
        }
        return null;
    }
}
