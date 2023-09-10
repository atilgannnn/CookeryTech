package com.cookerytech.dto;

import com.cookerytech.domain.Favorite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDTO {

    private Long id;


    private ModelDTO modelDTO;


    private Long userId;


    private LocalDateTime createAt;

    public FavoriteDTO(Favorite favorite, ModelDTO modelDTO) {
        this.id = favorite.getId();
        this.createAt = favorite.getCreateAt();
        this.userId = favorite.getUser().getId();
        this.modelDTO = modelDTO;

    }
}
