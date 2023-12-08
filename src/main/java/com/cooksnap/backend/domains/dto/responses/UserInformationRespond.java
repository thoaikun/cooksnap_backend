package com.cooksnap.backend.domains.dto.responses;


import com.cooksnap.backend.domains.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationRespond {
    private int userId;
    private String fullName;
    private Date dayOfBirth;
    private float height;
    private float weight;
    private Role role;
}
