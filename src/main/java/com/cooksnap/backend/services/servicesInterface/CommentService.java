package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.entity.Comment;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    List<Comment> getAllCommentForDish(String aboutDish);

    void postComment(String cmt, String aboutDish, Principal connectedUser);

}
