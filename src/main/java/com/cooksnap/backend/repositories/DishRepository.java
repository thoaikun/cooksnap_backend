package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.Dish;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Transactional
public interface DishRepository  extends JpaRepository<Dish, Integer> {
    @Query(value = "SELECT dish.dish_id, dish.about FROM dish INNER JOIN favorite_dish ON dish.dish_id = favorite_dish.dish_id where favorite_dish.favorite_list_id = :listId", nativeQuery = true)
    List<Dish> getFavoriteDish(@Param("listId") int listId);

    Dish findByAbout(String aboutDish);
}
