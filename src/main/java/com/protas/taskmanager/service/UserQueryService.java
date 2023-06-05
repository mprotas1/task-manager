package com.protas.taskmanager.service;

import com.protas.taskmanager.dto.UserResponseDto;
import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.parser.UserParser;
import com.protas.taskmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;
    private final UserParser userParser;

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the entity with id: " + id));

        return userParser.convertToDto(user);
    }

    public List<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(c -> userParser.convertToDto(c))
                .collect(Collectors.toList());
    }

}
