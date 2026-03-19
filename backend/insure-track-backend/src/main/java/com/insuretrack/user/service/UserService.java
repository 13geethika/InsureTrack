package com.insuretrack.user.service;

import com.insuretrack.user.dto.UserRequestDTO;
import com.insuretrack.user.dto.UserResponseDTO;
import java.util.List;

public interface UserService {
   public UserResponseDTO getUserById(Long userId);
   public List<UserResponseDTO> getAllUsers();

}
