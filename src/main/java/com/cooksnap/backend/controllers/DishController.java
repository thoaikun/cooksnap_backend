package com.cooksnap.backend.controllers;

import com.cooksnap.backend.domains.dto.requests.AddDishRequest;
import com.cooksnap.backend.services.servicesInterface.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/dishes/")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    //get favorite list by user id
    @GetMapping("list-favorite")
    public ResponseEntity<?> getFavoriteListByUserId(Principal connectedUser){
        return dishService.getFavoriteListByUserId(connectedUser);
    }

    //add new favorite list
    @PostMapping("list-favorite")
    public ResponseEntity<?> addFavoriteListByUserId(@Param("name") String name, Principal connectedUser){
        return dishService.addFavoriteListByUserId(name,connectedUser);
    }

    //delete favorite list
    @DeleteMapping("list-favorite")
    public ResponseEntity<?> deleteFavorite(@RequestParam int listId){
        return dishService.deleteFavorite(listId);
    }

    //get favorite dish by list id
    @GetMapping("list-dish")
    public ResponseEntity<?> getFavoriteDish(@RequestParam int listId){
        return dishService.getFavoriteDish(listId);
    }

    //add dish to favorite list
    @PostMapping("/dish/favorite-list")
    public ResponseEntity<?> addDishToFavoriteList(@RequestBody AddDishRequest request, Principal connectedUser){
        return dishService.addDishToFavoriteList(request,connectedUser);
    }

    //delete favorite dish in list
    @DeleteMapping("/dish/favorite-list")
    public ResponseEntity<?> deleteDishToFavoriteList(@RequestBody AddDishRequest request, Principal connectedUser){
        return dishService.deleteDishToFavoriteList(request,connectedUser);
    }
}
