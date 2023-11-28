package com.cooksnap.backend.services.servicesInterface;

import com.cooksnap.backend.domains.dto.requests.OTPRequest;
import com.cooksnap.backend.domains.dto.requests.ResetPasswordRequest;

public interface ResetPassword {
     String sendOTP(ResetPasswordRequest request);
     boolean submitOTP(OTPRequest OTP);
}
