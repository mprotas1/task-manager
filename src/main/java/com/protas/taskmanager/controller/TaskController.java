package com.protas.taskmanager.controller;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.service.TaskPagingService;
import com.protas.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/{userId}/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // get all tasks for specific User with {userId}
    @GetMapping
    public ResponseEntity<List<Task>> getAllUserTasks(@PathVariable Long userId) {
        List<Task> allTasks = taskService.getAllTasks(userId);
        return ResponseEntity.status(HttpStatus.OK).body(allTasks);
    }

    // get Task with {id} of User with {userId}
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long userId, @PathVariable Long id) {
        Task task = taskService.getTask(userId, id);
        return ResponseEntity.ok(task);
    }

    // create new Task for User with {userId}
    @PostMapping
    public ResponseEntity<Task> createTask(@PathVariable Long userId,
                                           @RequestBody Task task) {
        Task createdTask = taskService.createNewTask(userId, task);
        return ResponseEntity.accepted().body(createdTask);
    }

    // delete Task with {id} for User with {userId}
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long userId, @PathVariable Long id) {
        taskService.deleteTask(userId, id);
        return ResponseEntity.noContent().build();
    }

    // update Task with {id} for User with {userId}
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long userId, @PathVariable Long id,
                                                @RequestBody Task task) {
        Task taskToUpdate = taskService.updateTask(userId, id, task);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskToUpdate);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> changeTaskStatus(@PathVariable Long userId, @PathVariable Long id) {
        Task taskToUpdate = taskService.completeTask(userId, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskToUpdate);
    }
}
