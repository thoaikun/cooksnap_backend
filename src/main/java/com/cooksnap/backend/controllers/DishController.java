package com.cooksnap.backend.controllers;

import com.cooksnap.backend.domains.dto.requests.AddDishRequest;
import com.cooksnap.backend.services.servicesInterface.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get favorite list by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get favorite list succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @GetMapping("list-favorite")
    public ResponseEntity<?> getFavoriteListByUserId(Principal connectedUser){
        return dishService.getFavoriteListByUserId(connectedUser);
    }

    //add new favorite list
    @Operation(summary = "Add new favorite list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add new favorite list succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @PostMapping("list-favorite")
    public ResponseEntity<?> addFavoriteListByUserId(@Param("name") String name, Principal connectedUser){
        return dishService.addFavoriteListByUserId(name,connectedUser);
    }

    //delete favorite list
    @Operation(summary = "Delete favorite list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a favorite list succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
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
