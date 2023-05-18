package com.protas.taskmanager.controller;

import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.model.UserErrorResponse;
import com.protas.taskmanager.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // get all Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    // getting User by his ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)  {
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    // creating User with empty task list
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok(user);
    }

    // deleting User with specified Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // updating User with specified ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                                 @RequestBody User user) {
        userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
