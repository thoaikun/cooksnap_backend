package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.dto.requests.OTPRequest;
import com.cooksnap.backend.domains.dto.requests.ResetPasswordRequest;
import org.springframework.http.ResponseEntity;

public interface ResetPassword {
     ResponseEntity<?> sendOTP(ResetPasswordRequest request);
     ResponseEntity<?> submitOTP(OTPRequest OTP);
     ResponseEntity<?> submitOTPChangePassword(OTPRequest OTP);
}
