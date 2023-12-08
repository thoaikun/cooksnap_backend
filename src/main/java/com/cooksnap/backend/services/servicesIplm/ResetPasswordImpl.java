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
import com.cooksnap.backend.domains.entity.User;
import com.cooksnap.backend.repositories.OtpRepository;
import com.cooksnap.backend.repositories.TokenRepository;
import com.cooksnap.backend.repositories.UserRepository;
import com.cooksnap.backend.services.servicesInterface.EmailService;
import com.cooksnap.backend.services.servicesInterface.ResetPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> sendOTP(ResetPasswordRequest request) {
        try {
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String newPassword = generateRandomPassword(6);
            user.setPassword(passwordEncoder.encode(newPassword));
            EmailDetails email = EmailDetails
                    .builder()
                    .recipient(user.getEmail())
                    .msgBody("Your new password :" + newPassword + "\n")
                    .subject("Reset Password")
                    .build();
            emailService.sendTextMail(email);
            userRepository.save(user);
            return ResponseEntity.ok().body(newPassword);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("Email không tồn tại"));
        }
    }

    private static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        String allChars = UPPER + LOWER + DIGITS + SPECIAL_CHARS;
        String upperCase = ".*[A-Z].*";
        String lowerCase = ".*[a-z].*";
        String digit = ".*\\d.*";
        String special = ".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*";

        String regex = "(?=.*" + upperCase + ")(?=.*" + lowerCase + ")(?=.*" + digit + ")(?=.*" + special + ").{" + length + "}";

        do {
            stringBuilder.setLength(0);
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(allChars.length());
                stringBuilder.append(allChars.charAt(index));
            }
        } while (!stringBuilder.toString().matches(regex));

        return stringBuilder.toString();
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
