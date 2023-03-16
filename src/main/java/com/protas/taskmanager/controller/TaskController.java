package com.protas.taskmanager.controller;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user/{userId}/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // get all tasks for specific User with {userId}
    @GetMapping
    public List<Task> getAllUserTasks(@PathVariable Long userId) {
        return taskService.getAllTasks(userId);
    }

    // get Task with {id} of User with {userId}
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long userId, @PathVariable Long id) {
        Task task = taskService.getTask(userId, id);
        return ResponseEntity.ok(task);
    }

    // create new Task for User with {userId}
    @PostMapping
    public ResponseEntity<Task> createTask(@PathVariable Long userId, @RequestBody Task task) {
        Task createdTask = taskService.createNewTask(userId, task);

        return ResponseEntity.accepted().body(task);
    }

    // delete Task with {id} for User with {userId}
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long userId, @PathVariable Long id) {
        taskService.deleteTask(userId, id);

        return ResponseEntity.noContent().build();
    }

    //
}
