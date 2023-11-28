package com.cooksnap.backend.controllers;


import com.cooksnap.backend.domains.dto.requests.ChangePasswordRequest;
import com.cooksnap.backend.services.servicesInterface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserInformation(Principal connectedUser){
        return ResponseEntity.ok().body(userService.getUserInformation(connectedUser));
    }

    @PatchMapping("/new-password")
    public ResponseEntity<?> newPassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        return userService.newPassword(request, connectedUser);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
