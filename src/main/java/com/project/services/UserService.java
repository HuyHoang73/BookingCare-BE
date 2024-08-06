package com.project.services;

import com.project.dto.UserDTO;
import com.project.models.User;
import com.project.requests.UserSearchRequest;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, String> login(String username, String password) throws Exception;
    User createUser(UserDTO userDTO) throws Exception;
    UserDTO getUserById(Long id) throws Exception;
    List<UserDTO> getAllUsers(UserSearchRequest request);
    void deleteUserById(Long id) throws Exception;
}
