package com.insuretrack.user.dto;

import com.insuretrack.common.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Name must not be empty")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone must not be empty")
    // Example validation, adjust to your format (India sample): 10 digits
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 3, message = "Password must be at least 3 characters")
    private String password;

    @NotNull(message = "Role must not be empty")
    private UserRole role;

}
