package com.protas.taskmanager.dto;

import com.protas.taskmanager.model.Role;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
