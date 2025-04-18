package com.kavindu.commercehub.Authentication.Dto.Request;

import com.kavindu.commercehub.Authentication.models.AppUser;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdate {
    private Integer id;
    private AppUser appUser;
}
