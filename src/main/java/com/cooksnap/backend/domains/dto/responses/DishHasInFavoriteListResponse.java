package com.cooksnap.backend.domains.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishHasInFavoriteListResponse {
    private boolean isInFavoriteList;
    private List<Object> favoriteListIds;
}
