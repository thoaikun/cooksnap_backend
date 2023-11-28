package com.cooksnap.backend.services.servicesIplm;

import com.cooksnap.backend.config.JwtService;
import com.cooksnap.backend.domains.TokenType;
import com.cooksnap.backend.domains.dto.ErrorResponseDto;
import com.cooksnap.backend.domains.dto.SuccessResponse;
import com.cooksnap.backend.domains.dto.requests.EmailDetails;
import com.cooksnap.backend.domains.dto.requests.OTPRequest;
import com.cooksnap.backend.domains.dto.requests.ResetPasswordRequest;
import com.cooksnap.backend.domains.dto.responses.AuthenticationResponse;
import com.cooksnap.backend.domains.entity.Otp;
import com.cooksnap.backend.domains.entity.Token;
import com.cooksnap.backend.repositories.OtpRepository;
import com.cooksnap.backend.repositories.TokenRepository;
import com.cooksnap.backend.repositories.UserRepository;
import com.cooksnap.backend.services.servicesInterface.EmailService;
import com.cooksnap.backend.services.servicesInterface.ResetPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
public class ResetPasswordImpl implements ResetPassword {
    private final EmailService emailService;
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public ResponseEntity<?> sendOTP(ResetPasswordRequest request) {
        try {
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            if (otpRepository.findByUserId(user.getUserId()).isPresent()) {
                otpRepository.deleteByUserId(user.getUserId());
            }

            String otpCode = generateOTP();
            Otp newOtp = Otp
                    .builder()
                    .otpCode(otpCode)
                    .otpExpired(false)
                    .userId(user.getUserId())
                    .build();
            otpRepository.save(newOtp);
            EmailDetails email = EmailDetails
                    .builder()
                    .recipient(user.getEmail())
                    .msgBody("Your OTP Code :" + otpCode + "\n" + "OTP will be expired in 60 second")
                    .subject("Reset Password OTP")
                    .build();
            emailService.sendTextMail(email);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    otpRepository.deleteByOtpCode(otpCode);
                    timer.cancel();
                }
            }, 60000);
            return ResponseEntity.ok().body(otpCode);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("Email không tồn tại"));
        }
    }

    public ResponseEntity<?> submitOTP (OTPRequest OTP){
        try {
            var user = userRepository.findByEmail(OTP.getEmail()).orElseThrow(() -> new Exception("Email không đúng"));
            var otp = otpRepository.findByOtpCode(OTP.getOtp());
            if (otp.isPresent()) {
                if (!otp.get().getUserId().equals(user.getUserId())){
                    return ResponseEntity.badRequest().body(new ErrorResponseDto("OTP không đúng"));
                }
                Otp validateOtp = otp.get();
                otpRepository.deleteByUserId(validateOtp.getUserId());
                return ResponseEntity.ok().body(new SuccessResponse("Success"));
            }
            else {
                return ResponseEntity.badRequest().body(new ErrorResponseDto("OTP không đúng"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.toString()));
        }
    }

    public ResponseEntity<?> submitOTPChangePassword(OTPRequest OTP){
        try {
            var user = userRepository.findByEmail(OTP.getEmail()).orElseThrow(() -> new Exception("Email không tồn tại"));
            var otp = otpRepository.findByOtpCode(OTP.getOtp());
            if (otp.isPresent()) {
                if (!otp.get().getUserId().equals(user.getUserId())){
                    return ResponseEntity.badRequest().body(new ErrorResponseDto("OTP không đúng"));
                }
                Otp validateOtp = otp.get();
                otpRepository.deleteByUserId(validateOtp.getUserId());
                var jwtToken = jwtService.generateToken(user);
                var token = Token.builder()
                        .user(user)
                        .token(jwtToken)
                        .tokenType(TokenType.BEARER)
                        .expired(false)
                        .revoked(false)
                        .build();
                tokenRepository.save(token);
                return ResponseEntity.ok().body(AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .build());
            }
            else {
                return ResponseEntity.badRequest().body(new ErrorResponseDto("OTP không đúng"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.toString()));
        }
    };


    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
