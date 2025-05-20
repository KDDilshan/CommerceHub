package com.kavindu.commercehub.Authentication.repositories;

import com.kavindu.commercehub.Authentication.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByEmail(String email);

    Optional<AppUser>  findById(Integer userID);

    Optional<AppUser> findUserByEmail(String userEmail);

    Optional<AppUser> findByUsername(String username);
}
