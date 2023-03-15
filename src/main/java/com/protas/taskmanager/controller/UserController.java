package com.protas.taskmanager.controller;

import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // get all Users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // getting User by his ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)  {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    // creating User with empty task list
    @PostMapping
    public ResponseEntity<User> addStudent(@RequestBody User user) {

        userService.createUser(user);

        return ResponseEntity.ok(user);
    }

    // deleting User with specified Id
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteStudent(@PathVariable Long id) {

        User userToDelete = userService.getUserById(id);

        userService.deleteUser(userToDelete);
        return ResponseEntity.noContent().build();
    }

    // updating User with specified ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                                 @RequestBody User user) {

        User userToUpdate = userService.getUserById(id);

        userToUpdate.setUsername(user.getUsername());

        userService.updateUser(userToUpdate);

        return ResponseEntity.ok(userToUpdate);
    }

}
