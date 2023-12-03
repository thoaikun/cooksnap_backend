package com.cooksnap.backend.controllers;


import com.cooksnap.backend.domains.dto.SuccessResponse;
import com.cooksnap.backend.domains.dto.requests.ChangePasswordRequest;
import com.cooksnap.backend.domains.dto.responses.UserInformationRespond;
import com.cooksnap.backend.services.servicesInterface.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "get profile user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get profile successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserInformationRespond.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @GetMapping("/profile")
    public ResponseEntity<?> getUserInformation(Principal connectedUser){
        return ResponseEntity.ok().body(userService.getUserInformation(connectedUser));
    }


    @Operation(summary = "make new password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @PatchMapping("/new-password")
    public ResponseEntity<?> newPassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        return userService.newPassword(request, connectedUser);
    }

    @Operation(summary = "Change password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    }
    )
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
