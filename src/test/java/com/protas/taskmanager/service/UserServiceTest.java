package com.protas.taskmanager.service;

import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        // given
        User user = new User();
        user.setUsername("john123testtest");

        // when
        User savedUser = userService.createUser(user);

        // then
        assertNotNull(savedUser.getId());
        assertEquals("john123", savedUser.getUsername());
    }

    @Test
    public void testGetUser() {
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
}


