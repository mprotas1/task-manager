package com.protas.taskmanager.service;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.repository.TaskRepository;
import com.protas.taskmanager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(Long userId, Pageable page) {
        List<Task> tasks = taskRepository.findByUserId(userId, page)
                .getContent();

        return tasks;
    }

    public List<Task> getTasksByTitle(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the user with id: " + userId))
                .getTasks()
                .stream()
                .sorted(Comparator.comparing(Task::getTitle))
                .toList();
    }

    public Task getTask(Long userId, Long taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate entity"));

        Task task = taskRepository.findByUserAndId(user, taskId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate entity"));

        return task;
    }

    public Task createNewTask(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate entity"));

        List<Task> tasks = user.getTasks();
        Task taskToAdd = new Task(task.getTitle(), task.getContent(), user);
        taskToAdd.setTaskPriority(task.getTaskPriority());

        tasks.add(taskToAdd);

        return taskRepository.save(taskToAdd);
    }

    public Task updateTask(Long userId, Long taskId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate user entity"));

        Task taskToUpdate = taskRepository.findByUserAndId(user, taskId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate task entity"));

        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setContent(task.getContent());
        taskToUpdate.setCompleted(task.isCompleted());
        taskToUpdate.setTaskPriority(task.getTaskPriority());

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

    public void deleteTask(Long userId, Long taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate user entity"));

        Task taskToDelete = taskRepository.findByUserAndId(user, taskId)
                .orElseThrow(() -> new EntityNotFoundException("Did not find the appropriate task entity"));

        taskRepository.delete(taskToDelete);
    }
}
