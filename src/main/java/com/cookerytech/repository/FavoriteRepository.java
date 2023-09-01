package com.cookerytech.repository;

import com.cookerytech.domain.Favorite;
import com.cookerytech.domain.Model;
import com.cookerytech.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Favorite findByModelAndUser(Model model, User authenticatedUser);

    @Query("SELECT f FROM Favorite f JOIN f.user u WHERE u.id = :userId")
    List<Favorite> findAllByUserId(@Param("userId") Long userId);
}
