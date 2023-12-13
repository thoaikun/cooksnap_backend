package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.FavoriteDish;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Transactional
public interface FavoriteDishRepository extends JpaRepository<FavoriteDish, Integer> {
    @Modifying
    @Query(
        value = "DELETE FROM cooksnap_database.favorite_dish WHERE listId = :favoriteListId AND dish_id = :dishId",
        nativeQuery = true
    )
    void deleteFavoriteDishByDishIdAndFavoriteListId(@Param("dishId") String dishId, @Param("favoriteListId") int favoriteListId);

    void deleteByFavoriteListId(int listID);

    @Query(
        value = "SELECT favorite_dish.dish_id, favorite_dish.favorite_list_id, favorite_list.list_name, favorite_list.user_id  FROM favorite_dish JOIN favorite_list ON favorite_dish.favorite_list_id = favorite_list.id WHERE user_id = :userId AND dish_id = :dishId",
        nativeQuery = true
    )
    List<Object> getFavoriteDishWithUserId(@Param("dishId") String dishId, @Param("userId") int userId);
}
