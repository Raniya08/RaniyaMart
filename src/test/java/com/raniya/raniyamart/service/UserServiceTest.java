package com.raniya.raniyamart.service;

import com.raniya.raniyamart.dao.UserDAO;
import com.raniya.raniyamart.dto.UserLoginDTO;
import com.raniya.raniyamart.dto.UserRegisterDTO;
import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.exception.AuthenticationException;
import com.raniya.raniyamart.exception.ValidationException;
import com.raniya.raniyamart.model.User;
import com.raniya.raniyamart.service.impl.UserServiceImpl;
import com.raniya.raniyamart.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    void register_Success() {
        UserRegisterDTO dto = new UserRegisterDTO("Test User", "test@example.com", "Password123!", "BUYER");
        
        when(userDAO.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userDAO.create(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        UserResponseDTO response = userService.register(dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test User", response.getFullName());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("BUYER", response.getRole());
        verify(userDAO).create(any(User.class));
    }

    @Test
    void register_DuplicateEmail_ThrowsException() {
        UserRegisterDTO dto = new UserRegisterDTO("Test User", "test@example.com", "Password123!", "BUYER");
        when(userDAO.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(ValidationException.class, () -> userService.register(dto));
        verify(userDAO, never()).create(any(User.class));
    }

    @Test
    void authenticate_InvalidPassword_ThrowsException() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash(PasswordUtil.hashPassword("CorrectPass123!"));

        when(userDAO.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserLoginDTO dto = new UserLoginDTO("test@example.com", "WrongPass");

        assertThrows(AuthenticationException.class, () -> userService.authenticate(dto));
    }
}
