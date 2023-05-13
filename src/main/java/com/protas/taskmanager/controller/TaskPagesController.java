package com.protas.taskmanager.controller;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.service.TaskPagingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/{userId}/task")
public class TaskPagesController {

    private final TaskPagingService taskPagingService;

    public TaskPagesController(TaskPagingService taskPagingService) {
        this.taskPagingService = taskPagingService;
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Task>> getAllUserTasksByPage(@PathVariable Long userId,
                                                            @PathVariable Integer page) {
        Page<Task> allTasks = taskPagingService.getAllTasks(userId, page);
        return ResponseEntity.status(HttpStatus.OK).body(allTasks);
    }

    @GetMapping("/page/{page}/sorted")
    public ResponseEntity<Page<Task>> getAllUserTasksByPageSorted(@PathVariable Long userId,
                                                            @PathVariable Integer page) {
        Page<Task> allTasks = taskPagingService.getAllTasksSorted(userId, page);
        return ResponseEntity.status(HttpStatus.OK).body(allTasks);
    }
}
