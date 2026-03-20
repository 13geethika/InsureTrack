package com.insuretrack.user.dto;

import com.insuretrack.common.enums.Status;
import com.insuretrack.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    public Long userId;
    public String name;
    public String email;
    public UserRole role;
    public String phone;
    public String token;


}
