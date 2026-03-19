package com.insuretrack.insure_track_backend.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insuretrack.user.controller.UserController;
import com.insuretrack.user.dto.UserResponseDTO;
import com.insuretrack.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetUserById() throws Exception {

        UserResponseDTO response = new UserResponseDTO();
        response.setUserId(1L);
        response.setEmail("admin@gmail.com");

        when(userService.getUserById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/admin/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllUsers() throws Exception {

        UserResponseDTO user = new UserResponseDTO();
        user.setUserId(1L);
        user.setEmail("admin@gmail.com");

        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk());
    }
}
