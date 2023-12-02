package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.Rating;
import com.cooksnap.backend.domains.entity.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RatingRepository extends JpaRepository<Rating, RatingKey> {

    @Query(value = "SELECT rating.rate_score, rating.user_id, rating.dish_id FROM rating INNER JOIN dish ON dish.dish_id = rating.dish_id WHERE dish.about = :dishAbout AND rating.userId = :userId", nativeQuery = true)
    Optional<Rating> findRatingScoreByUserIdAndDishAbout(@Param("dishAbout") String dishAbout, @Param("userId") Integer userId);

    @Query(value = "SELECT rating.rate_score, rating.user_id, rating.dish_id FROM rating INNER JOIN dish ON dish.dish_id = rating.dish_id WHERE dish.about = :dishAbout", nativeQuery = true)
    List<Rating> findRatingsByDish(@Param("dishAbout") String dishAbout);
}
