package com.protas.taskmanager.service;

import com.protas.taskmanager.config.jwt.JwtUtils;
import com.protas.taskmanager.dto.UserRequestDto;
import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.model.AuthenticationRequest;
import com.protas.taskmanager.model.AuthenticationResponse;
import com.protas.taskmanager.model.Role;
import com.protas.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserRequestDto userRegisterDto) {
        User user = modelMapper.map(userRegisterDto, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String jwtToken = jwtUtils.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getUsername(),
                  request.getPassword()
          )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(format("Did not find user with %s username", request.getUsername())));

        String token = jwtUtils.generateToken(user);

        return new AuthenticationResponse(token);
    }

}
