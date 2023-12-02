package com.cooksnap.backend.services.servicesIplm;

import com.cooksnap.backend.domains.entity.Dish;
import com.cooksnap.backend.domains.entity.Rating;
import com.cooksnap.backend.domains.entity.User;
import com.cooksnap.backend.repositories.DishRepository;
import com.cooksnap.backend.repositories.RatingRepository;
import com.cooksnap.backend.services.servicesInterface.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final DishRepository dishRepository;
    @Override
    public void ratingForDish(Integer score, String aboutDish, Principal connectedUser) {
        Dish dish = getDishIfExistElseCreate(aboutDish);
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<Rating> ratingOptional = ratingRepository.findRatingScoreByUserIdAndDishAbout(aboutDish, user.getUserId());
        if (ratingOptional.isPresent()) {
            Rating rating1 = ratingOptional.get();
            rating1.setRateScore(score);
            ratingRepository.save(rating1);
        }
        else {
            Rating rating = Rating.builder()
                    .userId(user.getUserId())
                    .rateScore(score)
                    .dishId(dish.getDish_id())
                    .build();
            ratingRepository.save(rating);
        }

    }

    @Override
    public List<Rating> getAllRatingForDish(String aboutDish) {
        return ratingRepository.findRatingsByDish(aboutDish);
    }


    Dish getDishIfExistElseCreate(String aboutDish) {
        Optional<Dish> dishOptional = dishRepository.findByAbout(aboutDish);
        Dish dish;

        if (dishOptional.isEmpty()) {
            dish = Dish.builder()
                    .about(aboutDish)
                    .build();
            dishRepository.saveAndFlush(dish);
            dish = dishRepository.findByAbout(aboutDish).orElse(dish);
        }
        else
            dish = dishOptional.get();

        return dish;
    }
}
