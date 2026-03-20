package com.insuretrack.insure_track_backend.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insuretrack.common.enums.UserRole;
import com.insuretrack.user.controller.AuthController;
import com.insuretrack.user.dto.LoginRequestDTO;
import com.insuretrack.user.dto.UserRequestDTO;
import com.insuretrack.user.dto.UserResponseDTO;
import com.insuretrack.user.service.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegister() throws Exception {
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Test User");
        request.setEmail("test@gmail.com");
        request.setPhone("9876543210"); // match your @Pattern if any
        request.setPassword("12345678");
        request.setRole(UserRole.CUSTOMER); // enum, not String

        UserResponseDTO response = new UserResponseDTO();
        response.setEmail("test@gmail.com");

        when(authService.register(request)).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    @Test
    void testLogin() throws Exception {

        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("test@gmail.com");
        request.setPassword("123456");

        UserResponseDTO response = new UserResponseDTO();
        response.setEmail("test@gmail.com");

        when(authService.login(request)).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
