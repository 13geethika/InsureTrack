package com.insuretrack.insure_track_backend.user;

import com.insuretrack.common.enums.UserRole;
import com.insuretrack.user.entity.User;
import com.insuretrack.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserRepositoryTest userRepositoryTest;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {

        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("123456");
        user.setRole(UserRole.ADMIN);
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getUserId());
        assertEquals("test@gmail.com",savedUser.getEmail());
    }

    @Test
    void testFindById() {

        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("123456");
        user.setRole(UserRole.ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userRepository.save(user);

        Optional<User> result = userRepository.findById(user.getUserId());

        assertTrue(result.isPresent());
        assertEquals("test@gmail.com",result.get().getEmail());
    }
}
