package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.FavoriteDish;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

@Transactional
public interface FavoriteDishRepository extends JpaRepository<FavoriteDish, Integer> {
    void deleteByDishIdAndFavoriteListId(@Param("dishId") String dishId, @Param("favoriteListId") int favoriteListId);

    void deleteByFavoriteListId(int listID);

    @Query(
        value = "SELECT favorite_dish.dish_id, favorite_dish.favorite_list_id, favorite_list.list_name, favorite_list.user_id  FROM favorite_dish JOIN favorite_list ON favorite_dish.favorite_list_id = favorite_list.id WHERE user_id = :userId AND dish_id = :dishId",
        nativeQuery = true
    )
    List<Object> getFavoriteDishWithUserId(@Param("dishId") String dishId, @Param("userId") int userId);
}
