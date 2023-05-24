package com.protas.taskmanager.service;

import com.protas.taskmanager.dto.UserRequestDto;
import com.protas.taskmanager.dto.UserResponseDto;
import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the entity with id: " + id));

        return convertToDto(user);
    }

    public List<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto createUser(UserRequestDto user) {
        User createdUser = userRepository.save(this.convertToEntity(user));
        return convertToDto(createdUser);
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
        return convertToDto(user);
    }

    public UserResponseDto convertToDto(User user) {
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        return userDto;
    }

    private User convertToEntity(UserRequestDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

}
