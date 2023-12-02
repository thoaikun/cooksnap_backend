package com.cooksnap.backend.services.servicesIplm;

import com.cooksnap.backend.domains.entity.Comment;
import com.cooksnap.backend.domains.entity.Dish;
import com.cooksnap.backend.domains.entity.User;
import com.cooksnap.backend.repositories.CommentRepository;
import com.cooksnap.backend.repositories.DishRepository;
import com.cooksnap.backend.services.servicesInterface.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final DishRepository dishRepository;

    @Override
    public List<Comment> getAllCommentForDish(String aboutDish) {
        Dish dish = getDishIfExistElseCreate(aboutDish);

        return commentRepository.findByDish(dish);
    }

    @Override
    public void postComment(String cmt, String aboutDish, Principal connectedUser) {
        Dish dish = getDishIfExistElseCreate(aboutDish);
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Comment comment = Comment.builder()
                .text(cmt)
                .dish(dish)
                .user(user)
                .build();

        commentRepository.save(comment);
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
