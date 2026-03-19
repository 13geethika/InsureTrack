package com.insuretrack.insure_track_backend.user;

import com.insuretrack.common.enums.UserRole;
import com.insuretrack.common.exception.ResourceNotFoundException;
import com.insuretrack.user.entity.User;
import com.insuretrack.user.repository.UserRepository;
import com.insuretrack.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails() {

        User user = new User();
        user.setEmail("test@insure.com");
        user.setPassword("encodedPassword");
        user.setRole(UserRole.ADMIN);

        when(userRepository.findByEmail("test@insure.com"))
                .thenReturn(Optional.of(user));

        UserDetails result =
                userService.loadUserByUsername("test@insure.com");

        assertNotNull(result);
        assertEquals("test@insure.com", result.getUsername());
        verify(userRepository, times(1))
                .findByEmail("test@insure.com");
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {

        when(userRepository.findByEmail("wrong@insure.com"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername("wrong@insure.com"));

        verify(userRepository, times(1))
                .findByEmail("wrong@insure.com");
    }


    @Test
    void getUserById_ShouldThrowResourceNotFound_WhenMissing() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(999L));

        verify(userRepository, times(1)).findById(999L);
    }
    @Test
    void getAllUsers_ShouldReturnMappedList() {
        User u1 = new User();
        u1.setUserId(1L); u1.setName("A"); u1.setEmail("a@insure.com"); u1.setPhone("111"); u1.setRole(UserRole.ADMIN);

        User u2 = new User();
        u2.setUserId(2L); u2.setName("B"); u2.setEmail("b@insure.com"); u2.setPhone("222"); u2.setRole(UserRole.AGENT);

        when(userRepository.findAll()).thenReturn(java.util.List.of(u1, u2));

        var result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getName());
        assertEquals(UserRole.ADMIN, result.get(0).getRole());
        assertEquals("B", result.get(1).getName());
        assertEquals(UserRole.AGENT, result.get(1).getRole());

        verify(userRepository, times(1)).findAll();
    }
    @Test
    void getAllUsers_ShouldReturnEmptyList_WhenNoUsers() {
        when(userRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        var result = userService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }
    @Test
    void loadUserByUsername_ShouldThrow_WhenUserRoleIsNull() {
        User user = new User();
        user.setEmail("norole@insure.com");
        user.setPassword("encoded");
        user.setRole(null); // edge case

        when(userRepository.findByEmail("norole@insure.com")).thenReturn(Optional.of(user));

        assertThrows(NullPointerException.class, () -> userService.loadUserByUsername("norole@insure.com"));
    }
}
