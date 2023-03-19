package com.protas.taskmanager.service;

import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the entity with id: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {

        // Validating if there is any user with the same ID or Username
        if(user.getId() == null) {
            if(userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new IllegalArgumentException("User with username: " + user.getUsername() + " already exists!");
            }
        }
        // Case: user passed in JSON does have ID
        else if(userRepository.findById(user.getId()).isPresent()){
            throw new IllegalArgumentException("User with id: " + user.getId() + " already exists!");
        }

        // Saving the user to repository
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, User userToUpdate) {
        // get user from DB
        User userFromDb = userRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        // update his fields with "userToUpdate" fields
        userFromDb.setUsername(userToUpdate.getUsername());

        // save the user
        userRepository.save(userFromDb);
    }
}
