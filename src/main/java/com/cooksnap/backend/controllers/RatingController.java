package com.cooksnap.backend.controllers;

import com.cooksnap.backend.domains.dto.requests.PostRatingRequest;
import com.cooksnap.backend.domains.dto.requests.PostToGetAll;
import com.cooksnap.backend.domains.entity.Rating;
import com.cooksnap.backend.services.servicesInterface.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings/")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @Operation(summary = "Post a user's rating score about a dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post rating score succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @PostMapping("post")
    @ResponseStatus(HttpStatus.CREATED)
    public void postRating(@RequestBody PostRatingRequest postRatingRequest, Principal connectedUser) {
        Integer score = postRatingRequest.getScore();
        String aboutDish = postRatingRequest.getAboutDish();
        ratingService.ratingForDish(score, aboutDish, connectedUser);
    }

    @Operation(summary = "Post method to get all ratings in a specific dish's page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get rating list succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @PostMapping
    public ResponseEntity<List<Rating>> getAllRatingForDish(@RequestBody PostToGetAll postToGetAllRating) {
        String aboutDish = postToGetAllRating.getAboutDish();
        List<Rating> ratings = ratingService.getAllRatingForDish(aboutDish);
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }
}
