package com.cooksnap.backend.controllers;

import com.cooksnap.backend.domains.dto.requests.AuthenticationRequest;
import com.cooksnap.backend.domains.dto.requests.OTPRequest;
import com.cooksnap.backend.domains.dto.requests.ResetPasswordRequest;
import com.cooksnap.backend.services.servicesInterface.ResetPassword;
import com.cooksnap.backend.services.servicesIplm.AuthenticationServiceIplm;
import com.cooksnap.backend.domains.dto.requests.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationServiceIplm service;
  private final ResetPassword resetPassword;

  @PostMapping("/register")
  public ResponseEntity<?> register(
      @RequestBody @Valid RegisterRequest request
  ) {
    return service.register(request);
  }
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return service.authenticate(request);
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

  @PostMapping("/send-OTP")
  public ResponseEntity<?> sendOTPResetPassword (@RequestBody ResetPasswordRequest request) {
    return resetPassword.sendOTP(request);
  }

  @PostMapping("/submit-OTP")
  public ResponseEntity<?> submitOTPChangePassword(@RequestBody OTPRequest OTP){
    try {
      return resetPassword.submitOTPChangePassword(OTP);
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hệ thống xảy ra lỗi");
    }
  }
}
