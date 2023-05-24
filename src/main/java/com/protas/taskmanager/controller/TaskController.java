package com.protas.taskmanager.controller;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user/{userId}/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllUserTasks(@PathVariable Long userId,
                                                      @PageableDefault(value = Integer.MAX_VALUE) Pageable page) {
        List<Task> result = taskService.getAllTasks(userId, page);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long userId, @PathVariable Long id) {
        Task task = taskService.getTask(userId, id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@PathVariable Long userId,
                                           @RequestBody Task task) {
        Task createdTask = taskService.createNewTask(userId, task);
        return ResponseEntity.accepted().body(createdTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long userId, @PathVariable Long id) {
        taskService.deleteTask(userId, id);
        return ResponseEntity.noContent().build();
    }

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
