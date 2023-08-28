package com.cookerytech.service;


import com.cookerytech.domain.Favorite;
import com.cookerytech.domain.User;
import com.cookerytech.dto.FavoriteDTO;
import com.cookerytech.mapper.ProductMapper;
import com.cookerytech.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final ProductMapper productMapper;

    public FavoriteService(FavoriteRepository favoriteRepository, UserService userService, ProductMapper productMapper) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.productMapper = productMapper;
    }


    public List<FavoriteDTO> getFavoritesByCurrentlyUser() {

        User currentlyUser = userService.getCurrentUser();
        Long userId = currentlyUser.getId();
        List<Favorite>  favorites =  favoriteRepository.findAllByUserId(userId);

        List<FavoriteDTO> favoriteDTOS = favorites.stream().
                map(favorite ->
                        (new FavoriteDTO(favorite,productMapper.productToProductDTO(favorite.getProduct())))).
                collect(Collectors.toList());

        return favoriteDTOS;
    }
}