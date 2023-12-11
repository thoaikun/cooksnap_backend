package com.cooksnap.backend.services.servicesIplm;

import com.cooksnap.backend.domains.dto.ErrorResponseDto;
import com.cooksnap.backend.domains.dto.SuccessResponse;
import com.cooksnap.backend.domains.dto.responses.UserInformationRespond;
import com.cooksnap.backend.domains.entity.User;
import com.cooksnap.backend.repositories.UserRepository;
import com.cooksnap.backend.domains.dto.requests.ChangePasswordRequest;
import com.cooksnap.backend.services.servicesInterface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceIplm implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserInformationRespond getUserInformation(Principal connectedUser){
        var user_principal = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        User user = repository.findByUserId(user_principal.getUserId());
        UserInformationRespond information = new UserInformationRespond() ;
        information.setUserId(user.getUserId());
        information.setFullName(user.getFullName());
        information.setDayOfBirth(user.getDayOfBirth());
        information.setHeight(user.getHeight());
        information.setWeight(user.getWeight());
        information.setRole(user.getRole());
        information.setEmail(user.getEmail());
        return information ;
    }

    public ResponseEntity<?> newPassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto("Nhập lại khẩu mới không trùng khớp"));
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
        return ResponseEntity.ok().body(new SuccessResponse("Đổi mật khẩu mới thành công"));
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }
}
