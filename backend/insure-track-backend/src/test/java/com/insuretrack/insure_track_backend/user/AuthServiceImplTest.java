package com.insuretrack.insure_track_backend.user;

import com.insuretrack.user.service.AuthServiceImpl;
import com.insuretrack.common.enums.UserRole;
import com.insuretrack.config.JwtService;
import com.insuretrack.user.entity.User;
import com.insuretrack.user.repository.UserRepository;
import com.insuretrack.user.repository.AuditLogRepository;
import com.insuretrack.user.dto.LoginRequestDTO;
import com.insuretrack.user.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_ShouldReturnUserResponseDTO() {

        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("admin@insure.com");
        request.setPassword("password");

        User user = new User();
        user.setUserId(1L);
        user.setName("Admin");
        user.setEmail("admin@insure.com");
        user.setPassword("encodedPassword");
        user.setRole(UserRole.ADMIN);
        user.setPhone("9999999999");

        when(userRepository.findByEmail("admin@insure.com"))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken(any()))
                .thenReturn("mocked-jwt-token");

        UserResponseDTO response = authService.login(request);

        assertNotNull(response);
        assertEquals("admin@insure.com", response.getEmail());
        assertEquals("mocked-jwt-token", response.getToken());

        verify(userRepository, times(1))
                .findByEmail("admin@insure.com");

        verify(jwtService, times(1))
                .generateToken(user);
    }
    @Test
    void login_ShouldThrow_WhenUserNotFound() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("nouser@insure.com");
        request.setPassword("whatever");

        when(userRepository.findByEmail("nouser@insure.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login(request));
        verify(userRepository, times(1)).findByEmail("nouser@insure.com");
        verify(jwtService, never()).generateToken(any());
    }
    @Test
    void login_ShouldReturnResponseWithMappedFields() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("admin@insure.com");
        request.setPassword("password");

        User user = new User();
        user.setUserId(7L);
        user.setName("Root Admin");
        user.setEmail("admin@insure.com");
        user.setPassword("encodedPassword");
        user.setRole(UserRole.ADMIN);
        user.setPhone("7777777777");

        when(userRepository.findByEmail("admin@insure.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("jwt");

        UserResponseDTO res = authService.login(request);

        assertEquals(7L, res.getUserId());
        assertEquals("Root Admin", res.getName());
        assertEquals("admin@insure.com", res.getEmail());
        assertEquals("7777777777", res.getPhone());
        assertEquals(UserRole.ADMIN, res.getRole());
        assertEquals("jwt", res.getToken());
    }

    @Test
    void login_ShouldGenerateTokenWithSameUserInstance() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("admin@insure.com");
        request.setPassword("password");

        User user = new User();
        user.setUserId(1L);
        user.setName("Admin");
        user.setEmail("admin@insure.com");
        user.setPassword("encodedPassword");
        user.setRole(UserRole.ADMIN);

        when(userRepository.findByEmail("admin@insure.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("token-123");

        UserResponseDTO response = authService.login(request);

        assertEquals("token-123", response.getToken());
        verify(jwtService, times(1)).generateToken(user);
    }
    @Test
    void login_ShouldThrow_WhenEmailIsNull() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail(null);
        request.setPassword("password");

        assertThrows(NoSuchElementException.class, () -> authService.login(request));
        verify(userRepository, never()).findByEmail(anyString());
    }
}
