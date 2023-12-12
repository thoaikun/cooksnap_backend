package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.dto.requests.AddDishRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface DishService {
    ResponseEntity<?> addFavoriteListByUserId(String name,Principal connectedUser);
    ResponseEntity<?> getFavoriteListByUserId(Principal connectedUser);
    ResponseEntity<?> getFavoriteDish(int listId);
    ResponseEntity<?> addDishToFavoriteList(AddDishRequest request, Principal connectedUser);
    ResponseEntity<?> deleteDishToFavoriteList(AddDishRequest request, Principal connectedUser);

    ResponseEntity<?> deleteFavorite(int listId);

    ResponseEntity<?> isInYourFavorite(String dishId, Principal connectedUser);
}
