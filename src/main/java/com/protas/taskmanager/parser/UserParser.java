package com.protas.taskmanager.parser;

import com.protas.taskmanager.dto.UserRequestDto;
import com.protas.taskmanager.dto.UserResponseDto;
import com.protas.taskmanager.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserParser {
    private final ModelMapper modelMapper;

    public UserResponseDto convertToDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    public User convertToEntity(UserRequestDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}

