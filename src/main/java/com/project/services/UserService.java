package com.project.services;

import java.util.Map;

public interface UserService {
    Map<String, String> login(String username, String password) throws Exception;
}
