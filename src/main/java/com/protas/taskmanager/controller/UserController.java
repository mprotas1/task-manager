package com.protas.taskmanager.controller;

import com.protas.taskmanager.dto.UserRequestDto;
import com.protas.taskmanager.dto.UserResponseDto;
import com.protas.taskmanager.service.UserCrudService;
import com.protas.taskmanager.service.UserQueryService;
import com.protas.taskmanager.validation.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserValidator userValidator;
    private final UserCrudService userCrudService;
    private final UserQueryService userQueryService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
            Authentication authentication,
            @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {

        System.out.println(">>> Authentication: " + authentication.getName());

        Collection<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        System.out.println(">>> Name: " + authentication.getName());
        System.out.println(">>> Authorities: " + authorities);
        System.out.println(">>> Credentials: " + authentication.getCredentials());

        List<UserResponseDto> resultList = userQueryService.getAllUsers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userValidator.hasId(#id)")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id)  {
        UserResponseDto user = userQueryService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody @Valid UserRequestDto user) {
        UserResponseDto userResponse = userCrudService.createUser(user);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userValidator.hasId(#id)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userCrudService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("@userValidator.hasId(#id)")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                 @RequestBody @Valid UserRequestDto user) {
        userCrudService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
