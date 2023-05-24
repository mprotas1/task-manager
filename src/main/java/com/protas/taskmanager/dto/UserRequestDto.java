package com.protas.taskmanager.dto;

import com.protas.taskmanager.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
public class UserRequestDto {
    private String username;
    private String email;
    private String password;
    private Role role;
}
