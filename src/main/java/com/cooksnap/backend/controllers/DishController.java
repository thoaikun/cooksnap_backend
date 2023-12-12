package com.cooksnap.backend.controllers;

import com.cooksnap.backend.domains.dto.SuccessResponse;
import com.cooksnap.backend.domains.dto.requests.AddDishRequest;
import com.cooksnap.backend.domains.entity.Dish;
import com.cooksnap.backend.domains.entity.FavoriteList;
import com.cooksnap.backend.services.servicesInterface.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes/")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    //get favorite list by user id
    @Operation(summary = "Get favorite list by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of favorite list by userId", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FavoriteList.class)))
            }),
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
            @ApiResponse(responseCode = "200", description = "Successful add new favorite list", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
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
            @ApiResponse(responseCode = "200", description = "Delete a favorite list successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @DeleteMapping("list-favorite")
    public ResponseEntity<?> deleteFavorite(@RequestParam int listId){
        return dishService.deleteFavorite(listId);
    }



    //get favorite dish by list id
    @Operation(summary = "Get favorite dish by list id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list dish successfully", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Dish.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @GetMapping("list-dish")
    public ResponseEntity<?> getFavoriteDish(@RequestParam int listId){
        return dishService.getFavoriteDish(listId);
    }





    //add dish to favorite list
    @Operation(summary = "add dish to favorite list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "add dish to favorite list successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @PostMapping("/dish/favorite-list")
    public ResponseEntity<?> addDishToFavoriteList(@RequestBody AddDishRequest request, Principal connectedUser){
        return dishService.addDishToFavoriteList(request,connectedUser);
    }

    //delete favorite dish in list
    @Operation(summary = "add dish to favorite list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete favorite dish in list successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @DeleteMapping("/dish/favorite-list")
    public ResponseEntity<?> deleteDishToFavoriteList(@RequestBody AddDishRequest request, Principal connectedUser){
        return dishService.deleteDishToFavoriteList(request,connectedUser);
    }

    @Operation(summary = "Check whether user has like this dish")
    @GetMapping("is-in-your-favorite")
    public ResponseEntity<?> isInYourFavorite(@RequestParam String dishId, Principal connectedUser) {
        return dishService.isInYourFavorite(dishId, connectedUser);
    }
}
