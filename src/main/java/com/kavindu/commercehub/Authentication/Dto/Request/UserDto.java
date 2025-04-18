package com.kavindu.commercehub.Authentication.Dto.Request;

import com.kavindu.commercehub.Authentication.models.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;

    public UserDto(AppUser user) {
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.email=user.getEmail();

    }
}
