package com.project.services.impl;

import com.project.components.JwtTokenUtil;
import com.project.exceptions.DataNotFoundException;
import com.project.models.User;
import com.project.repositories.RoleRepository;
import com.project.repositories.UserRepository;
import com.project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Override
    public Map<String, String> login(String username, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new DataNotFoundException("Tài khoản không tồn tại");
        }
        User existingUser = optionalUser.get();
        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không chính xác");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password,
                existingUser.getAuthorities()
        );
        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        String token = jwtTokenUtil.generateToken(existingUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("role", existingUser.getAuthorities().iterator().next().getAuthority());
        map.put("userId", String.valueOf(existingUser.getId()));
        return map;
    }
}
