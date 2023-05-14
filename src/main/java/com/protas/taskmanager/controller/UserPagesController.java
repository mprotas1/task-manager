package com.protas.taskmanager.controller;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.service.TaskPagingService;
import com.protas.taskmanager.service.UserPagingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserPagesController {

    private final UserPagingService userPagingService;

    public UserPagesController(UserPagingService userPagingService) {
        this.userPagingService = userPagingService;
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<User>> getAllUserTasksByPage(@PathVariable Integer page) {
        Page<User> allUsers = userPagingService.getAllUsersByPage(page);
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    @GetMapping("/page/{page}/sorted")
    public ResponseEntity<Page<User>> getAllUserTasksByPageSorted(@PathVariable Integer page) {
        Page<User> allUsers = userPagingService.getAllUsersByPageSorted(page);
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

}
