package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.dto.requests.ChangePasswordRequest;
import com.cooksnap.backend.domains.dto.responses.UserInformationRespond;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface UserService {
    UserInformationRespond getUserInformation(Principal connectedUser);
    ResponseEntity<?> newPassword(ChangePasswordRequest request, Principal connectedUser);

    void changePassword(ChangePasswordRequest request, Principal connectedUser);
}
