package com.protas.taskmanager.service;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.entity.User;
import jakarta.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    // POST method positive result
    @Test
    public void shouldAddNewTask() {
        // given
        String title = "Some title";
        String content = "Some content";
        boolean isCompleted = false;

        User user = userService.createUser(new User("SomeUsername"));
        Task taskToAdd = Task.builder()
                .title(title)
                .content(content)
                .taskPriority(5)
                .isCompleted(false)
                .build();

        // when
        Task task = taskService.createNewTask(user.getId(),
                taskToAdd);

        // then
        Assertions.assertNotNull(task);
        Assertions.assertEquals(user.getTasks().size(), 1);
        Assertions.assertEquals(task.getTitle(), title);
    }

    // POST method rejection with wrong task's priority
    @Test
    public void shouldNotAddTaskWithWrongPriority() {
        // given
        String title = "Some title";
        String content = "Some content";
        int priority = 12; // out of range
        boolean isCompleted = false;

        User user = userService.createUser(new User("SomeUsername"));
        Task taskToAdd = Task.builder()
                .title(title)
                .content(content)
                .taskPriority(priority)
                .isCompleted(false)
                .build();

        // when + then
        assertThatThrownBy(() -> taskService.createNewTask(user.getId(), taskToAdd))
                .isInstanceOf(ConstraintViolationException.class);
    }

    // ADD task rejection with wrong task's title
    @Test
    public void shouldNotAddTaskWithWrongTitle() {
        // given
        String title = "";
        String content = "Some content";
        int priority = 5; // out of range
        boolean isCompleted = false;

        User user = userService.createUser(new User("SomeUsername"));
        Task taskToAdd = Task.builder()
                .title(title)
                .content(content)
                .taskPriority(priority)
                .isCompleted(false)
                .build();

        // when + then
        assertThatThrownBy(() -> taskService.createNewTask(user.getId(), taskToAdd))
                .isInstanceOf(ConstraintViolationException.class);
    }

    // ADD task rejection with wrong task's content
    @Test
    public void shouldNotAddTaskWithWrongContent() {
        // given
        String title = "Some title";
        String content = "";
        int priority = 4; // out of range
        boolean isCompleted = false;

        User user = userService.createUser(new User("SomeUsername"));
        Task taskToAdd = Task.builder()
                .title(title)
                .content(content)
                .taskPriority(priority)
                .isCompleted(false)
                .build();

        // when + then
        assertThatThrownBy(() -> taskService.createNewTask(user.getId(), taskToAdd))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void shouldGetAllUsersTask() {
        // given
        String title = "Some title";
        String content = "Some content";
        int priority = 7;
        boolean isCompleted = false;

        User user = userService.createUser(new User("SomeUsername"));
        Task taskToAdd = Task.builder()
                .title(title)
                .content(content)
                .taskPriority(priority)
                .isCompleted(false)
                .build();

        // when
        taskService.createNewTask(user.getId(), taskToAdd);
        taskService.createNewTask(user.getId(), taskToAdd);
        taskService.createNewTask(user.getId(), taskToAdd);

        // then
        List<Task> tasks = taskService.getAllTasks(user.getId());
        Assertions.assertEquals(tasks.size(), 3);
    }

    // GET task method positive result
    @Test
    public void shouldGetTaskById() {
        // given
        String title = "Some title";
        String content = "Some content";
        int priority = 7;
        boolean isCompleted = false;

        User user = userService.createUser(new User("SomeUsername"));
        Task taskToAdd = Task.builder()
                .title(title)
                .content(content)
                .taskPriority(priority)
                .isCompleted(false)
                .build();

        // when
        Long userId = user.getId();
        Task savedTask = taskService.createNewTask(userId, taskToAdd);
        Long taskId = savedTask.getId();
        Task receivedTask = taskService.getTask(userId, taskId);

        // then
        assertNotNull(receivedTask);
        assertEquals(receivedTask, savedTask);
    }

    // PUT method task positive result
    @Test
    public void shouldUpdateUsersTask() {
        // given
        String title = "Some title";
        String content = "Some content";
        int priority = 1;
        boolean isCompleted = false;

        User user = userService.createUser(new User("SomeUsername"));
        Task taskCreated = Task.builder()
                .title(title)
                .content(content)
                .taskPriority(priority)
                .build();
        Task task = taskService.createNewTask(user.getId(), taskCreated);

        // when
        Task updatedTask = taskService.updateTask(
                user.getId(),
                task.getId(),
                Task.builder()
                .title("Some updated title")
                .content("Some updated content")
                .taskPriority(5)
                .build()
        );

        // then
        Assertions.assertFalse(updatedTask.getTitle().equals(taskCreated.getTitle()));
        Assertions.assertNotEquals(updatedTask.getContent(), taskCreated.getContent());
        Assertions.assertNotEquals(updatedTask.getTaskPriority(), taskCreated.getTaskPriority());
        Assertions.assertEquals(task.getId(), updatedTask.getId());
        Assertions.assertNotNull(updatedTask);
    }
}
