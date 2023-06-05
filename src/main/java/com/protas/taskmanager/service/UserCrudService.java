package com.protas.taskmanager.service;

import com.protas.taskmanager.dto.UserRequestDto;
import com.protas.taskmanager.dto.UserResponseDto;
import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.parser.UserParser;
import com.protas.taskmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCrudService {
    private final UserRepository userRepository;
    private final UserParser userParser;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createUser(UserRequestDto user) {
        User createdUser = userRepository.save(userParser.convertToEntity(user));
        return userParser.convertToDto(createdUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userToUpdate) {

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the entity with id: " + id));

        userFromDb.setUsername(userToUpdate.getUsername());
        userFromDb.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
        userFromDb.setEmail(userToUpdate.getEmail());

        User user = userRepository.save(userFromDb);
        return userParser.convertToDto(user);
    }

}
