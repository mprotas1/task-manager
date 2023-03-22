package com.protas.taskmanager.service;

import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    // POST method positive result
    @Test
    public void shouldCreateUserWithCorrectName() {
        // given
        String username = "testusernamee";
        User user = new User();
        user.setUsername(username);

        // when
        User savedUser = userService.createUser(user);

        // then
        assertNotNull(savedUser.getId());
        assertEquals(username, savedUser.getUsername());
    }

    // POST method reject
    @Test
    public void shouldNotCreateUserWithIncorrectUsername() {
        String username = "John Doe"; // incorrect username with blank space
        User user = new User();
        user.setUsername(username);

        // when + then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(ConstraintViolationException.class);
    }

    // GET method positive result
    @Test
    public void shouldGetUserByID() {
        // given
        User userToTest = new User();
        userToTest.setUsername("testUser123");

        // when
        User savedUser = userService.createUser(userToTest);
        User receivedUser = userService.getUserById(savedUser.getId());

        // then
        assertNotNull(receivedUser);
        assertEquals(receivedUser, savedUser);
    }

    // GET method reject
    @Test
    public void shouldNotGetUserWithIncorrectId() {
        // given
        String username = "someTestUser";
        User userToTest = new User();
        userToTest.setUsername(username);

        // when
        User savedUser = userService.createUser(userToTest);
        System.out.println("Saved: " + savedUser.toString());

        Long id = savedUser.getId() + 1;

        // then
        assertThatThrownBy(() -> userService.getUserById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    // UPDATE/PUT method positive result
    @Test
    public void shouldUpdateUser() {
        // given
        String newUsername = "newUsername";
        User userData = new User(newUsername);

        // when
        User userToUpdate = userService.createUser(new User("oldUsername"));
        Long id = userToUpdate.getId();
        User userUpdated = userService.updateUser(id, userData);

        // then
        assertEquals(userToUpdate.getId(), userUpdated.getId());
        assertEquals(userUpdated.getUsername(), userData.getUsername());
    }

    // DELETE method positive result
    @Test
    public void shouldDeleteUser() {
        // given
        User userData = new User("testUserName");

        // when
        User createdUser = userService.createUser(userData); // creating user
        Long userId = createdUser.getId();
        userService.deleteUser(userId); // delete user with userId

        // then
        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(EntityNotFoundException.class);
    }
}


