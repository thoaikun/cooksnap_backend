package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.entity.Rating;

import java.security.Principal;
import java.util.List;

public interface RatingService {
    void ratingForDish(Integer score, String aboutDish, Principal connectedUser);

    List<Rating> getAllRatingForDish(String aboutDish);
}
