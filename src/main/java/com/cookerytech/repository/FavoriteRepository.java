package com.cookerytech.repository;
import com.cookerytech.domain.Favorite;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("SELECT f FROM Favorite f JOIN f.user u WHERE u.id = :userId")
    List<Favorite> findAllByUserId(@Param("userId") Long userId);
}