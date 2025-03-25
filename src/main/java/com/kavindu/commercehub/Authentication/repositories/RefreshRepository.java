package com.kavindu.commercehub.Authentication.repositories;

import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Authentication.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String token);
    RefreshToken findByUser(AppUser user);
    Optional<RefreshToken> findByUserEmail(String email);
    void delete(RefreshToken refreshToken);

    List<RefreshToken> findAllByUserEmail(String email);

}
