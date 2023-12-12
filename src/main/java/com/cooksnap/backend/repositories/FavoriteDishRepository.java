package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.FavoriteDish;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface FavoriteDishRepository extends JpaRepository<FavoriteDish, Integer> {
    void deleteByDishIdAndFavoriteListId(@Param("dishId") String dishId, @Param("favoriteListId") int favoriteListId);

    void deleteByFavoriteListId(int listID);
}
