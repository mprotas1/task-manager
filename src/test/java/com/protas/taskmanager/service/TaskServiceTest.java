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
import org.w3c.dom.ranges.Range;
import org.w3c.dom.ranges.RangeException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
}
