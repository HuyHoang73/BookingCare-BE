package com.project.services;

import com.project.dto.UserDTO;
import com.project.models.User;

import java.util.Map;

public interface UserService {
    Map<String, String> login(String username, String password) throws Exception;
    User createUser(UserDTO userDTO) throws Exception;
}
