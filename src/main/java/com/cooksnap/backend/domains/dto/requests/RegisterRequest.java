package com.cooksnap.backend.domains.dto.requests;

import com.cooksnap.backend.domains.role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterRequest {
  @Email(message = "Email không hợp lệ")
  private String email;
  private String password;
}
