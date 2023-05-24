package com.protas.taskmanager.controller;

import com.protas.taskmanager.dto.UserRequestDto;
import com.protas.taskmanager.dto.UserResponseDto;
import com.protas.taskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {

        List<UserResponseDto> resultList = userService.getAllUsers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id)  {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody @Valid UserRequestDto user) {
        UserResponseDto userResponse = userService.createUser(user);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                 @RequestBody @Valid UserRequestDto user) {
        userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
