package com.protas.taskmanager.controller;

import com.protas.taskmanager.dto.UserRequestDto;
import com.protas.taskmanager.model.AuthenticationRequest;
import com.protas.taskmanager.model.AuthenticationResponse;
import com.protas.taskmanager.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserRequestDto userRequestDto
    ) {
        return ResponseEntity.ok(authenticationService.register(userRequestDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
