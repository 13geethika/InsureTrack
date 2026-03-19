package com.insuretrack.common.response;

import com.insuretrack.user.dto.UserResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private String message;
    private UserResponseDTO data;
}
