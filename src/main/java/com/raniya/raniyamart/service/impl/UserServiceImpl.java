package com.raniya.raniyamart.service.impl;

import com.raniya.raniyamart.dao.UserDAO;
import com.raniya.raniyamart.dao.impl.UserDAOImpl;
import com.raniya.raniyamart.dto.UserLoginDTO;
import com.raniya.raniyamart.dto.UserRegisterDTO;
import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.exception.AuthenticationException;
import com.raniya.raniyamart.exception.ResourceNotFoundException;
import com.raniya.raniyamart.exception.ValidationException;
import com.raniya.raniyamart.model.User;
import com.raniya.raniyamart.service.UserService;
import com.raniya.raniyamart.util.PasswordUtil;
import com.raniya.raniyamart.util.ValidationUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserResponseDTO register(UserRegisterDTO dto) {
        // Business Rule & Input Validation
        if (dto == null) {
            throw new ValidationException("Registration payload is missing.");
        }
        ValidationUtil.validateNotEmpty(dto.getFullName(), "Full Name");
        ValidationUtil.validateEmail(dto.getEmail());
        ValidationUtil.validatePassword(dto.getPassword());

        String role = dto.getRole() != null ? dto.getRole().toUpperCase() : "BUYER";
        if (!"BUYER".equals(role) && !"SELLER".equals(role)) {
            throw new ValidationException("Invalid role selected. Only BUYER or SELLER registrations allowed.");
        }

        if (userDAO.findByEmail(dto.getEmail().toLowerCase().trim()).isPresent()) {
            throw new ValidationException("An account with email " + dto.getEmail() + " already exists.");
        }

        User user = new User();
        user.setFullName(ValidationUtil.sanitize(dto.getFullName()));
        user.setEmail(dto.getEmail().toLowerCase().trim());
        user.setPasswordHash(PasswordUtil.hashPassword(dto.getPassword()));
        user.setRole(role);

        User savedUser = userDAO.create(user);
        return new UserResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO authenticate(UserLoginDTO dto) {
        if (dto == null) {
            throw new ValidationException("Login payload is missing.");
        }
        ValidationUtil.validateEmail(dto.getEmail());
        ValidationUtil.validateNotEmpty(dto.getPassword(), "Password");

        Optional<User> userOpt = userDAO.findByEmail(dto.getEmail().toLowerCase().trim());
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid email or password.");
        }

        User user = userOpt.get();
        if (!PasswordUtil.checkPassword(dto.getPassword(), user.getPasswordHash())) {
            throw new AuthenticationException("Invalid email or password.");
        }

        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO getById(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("Invalid user ID.");
        }
        User user = userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return new UserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userDAO.findAll().stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }
}
