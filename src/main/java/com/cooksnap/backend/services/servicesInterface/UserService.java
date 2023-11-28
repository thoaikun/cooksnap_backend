package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.dto.requests.ChangePasswordRequest;
import com.cooksnap.backend.domains.dto.requests.UserInformationRequest;
import com.cooksnap.backend.domains.dto.responses.UserInformationRespond;

import java.security.Principal;

public interface UserService {
    UserInformationRespond getUserInformation(UserInformationRequest request);
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
}
