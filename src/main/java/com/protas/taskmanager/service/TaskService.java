package com.protas.taskmanager.service;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.repository.TaskRepository;
import com.protas.taskmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TaskService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // get all tasks from user
    public List<Task> getAllTasks(Long userId) {
        // in case of Lazy loading get tasks by "get" method
        List<Task> tasks = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the "))
                .getTasks();

        return tasks;
    }

    // get all tasks of user sorted by the title
    public List<Task> getTasksByTitle(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the user with id: " + userId))
                .getTasks()
                .stream()
                .sorted(Comparator.comparing(c -> c.getTitle()))
                .toList();
    }

    // get task with User {userId} and Task {taskId}
    public Task getTask(Long userId, Long taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate entity"));

        Task task = taskRepository.findByUserAndId(user, taskId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate entity"));

        return task;
    }

    // creating a new task for user with specific ID
    public Task createNewTask(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate entity"));

        List<Task> tasks = user.getTasks();
        Task taskToAdd = new Task(task.getTitle(), task.getContent(), user);

        if(task.getTaskPriority() != null) {
            taskToAdd.setTaskPriority(task.getTaskPriority());
        }

        tasks.add(taskToAdd);

        return taskRepository.save(taskToAdd);
    }

    // updating the existing task
    public Task updateTask(Long userId, Long taskId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate user entity"));

        Task taskToUpdate = taskRepository.findByUserAndId(user, taskId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate task entity"));

        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setContent(task.getContent());
        taskToUpdate.setCompleted(task.isCompleted());

        if(task.getTaskPriority() != null) {
            taskToUpdate.setTaskPriority(task.getTaskPriority());
        }

        return taskRepository.save(taskToUpdate);
    }

    public Task completeTask(Long userId, Long taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate user entity"));

        Task taskToUpdate = taskRepository.findByUserAndId(user, taskId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate task entity"));

        taskToUpdate.setCompleted(!taskToUpdate.isCompleted());

        return taskRepository.save(taskToUpdate);
    }

    // deleting task with specific ID for User
    public void deleteTask(Long userId, Long taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate user entity"));

        Task taskToDelete = taskRepository.findByUserAndId(user, taskId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate task entity"));

        taskRepository.delete(taskToDelete);
    }
}
