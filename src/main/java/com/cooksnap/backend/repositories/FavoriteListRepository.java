package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.FavoriteList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Transactional
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Integer> {
    List<FavoriteList> findByUserId(@Param("userId") int userId);
}
