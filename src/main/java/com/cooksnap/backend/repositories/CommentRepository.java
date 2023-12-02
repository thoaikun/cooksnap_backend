package com.cooksnap.backend.repositories;

import com.cooksnap.backend.domains.entity.Comment;
import com.cooksnap.backend.domains.entity.Dish;
import com.cooksnap.backend.domains.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByDish(Dish dish);

}