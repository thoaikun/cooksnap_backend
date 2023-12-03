package com.cooksnap.backend.controllers;

import com.cooksnap.backend.domains.dto.requests.PostCommentRequest;
import com.cooksnap.backend.domains.dto.requests.PostToGetAll;
import com.cooksnap.backend.domains.dto.responses.GetAllCommentResponse;
import com.cooksnap.backend.domains.entity.Comment;
import com.cooksnap.backend.services.servicesInterface.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Post a user's commment about a dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post comment succesfully",
                    content = { @Content(mediaType = "application/json") }
            ),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
            }
    )
    @PostMapping("post")
    @ResponseStatus(HttpStatus.CREATED)
    public void postComment(@RequestBody PostCommentRequest postCommentRequest, Principal connectedUser) {
        String cmt = postCommentRequest.getCmt();
        String about = postCommentRequest.getAboutDish();
        commentService.postComment(cmt, about, connectedUser);
    }

    @Operation(summary = "Post method to get the comment section in a specific dish's page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get comment list succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @PostMapping
    public ResponseEntity<List<GetAllCommentResponse>> getAllCommentForDish(@RequestBody PostToGetAll postToGetAllComment) {
        List<Comment> comments = commentService.getAllCommentForDish(postToGetAllComment.getAboutDish());
        List<GetAllCommentResponse> getAllCommentResponses = comments.stream().map(
                    comment -> GetAllCommentResponse.builder()
                            .userId(comment.getUser().getUserId())
                            .commentText(comment.getText())
                            .userName(comment.getUser().getFullName())
                            .build()
        ).toList();
        return ResponseEntity.status(HttpStatus.OK).body(getAllCommentResponses);
    }


}
