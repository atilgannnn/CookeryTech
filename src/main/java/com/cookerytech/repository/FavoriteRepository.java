package com.cookerytech.repository;
import com.cookerytech.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}