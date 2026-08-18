package com.raniya.raniyamart.service;

import com.raniya.raniyamart.dto.UserLoginDTO;
import com.raniya.raniyamart.dto.UserRegisterDTO;
import com.raniya.raniyamart.dto.UserResponseDTO;
import java.util.List;

public interface UserService {
    UserResponseDTO register(UserRegisterDTO dto);
    UserResponseDTO authenticate(UserLoginDTO dto);
    UserResponseDTO getById(Long id);
    List<UserResponseDTO> getAllUsers();
}
