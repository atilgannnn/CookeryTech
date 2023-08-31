package com.cookerytech.service;


import com.cookerytech.domain.Cart;
import com.cookerytech.domain.Cart_Items;
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

    private final CartService cartService;



    public FavoriteService(FavoriteRepository favoriteRepository, UserService userService, ProductMapper productMapper, CartService cartService) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.productMapper = productMapper;

        this.cartService = cartService;

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

    public void deleteAllFavorites() {

        favoriteRepository.deleteAll();

    }


    public void moveUsersFavoritesToCart() {  //K04
        //currently users favorites
      List<FavoriteDTO> usersFavorites = getFavoritesByCurrentlyUser();


        for (FavoriteDTO userFavorite:usersFavorites) {
          //  cartService.manageCartItem(userFavorite.getModelDTO().getId(),1)

        }

    }


}