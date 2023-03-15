package com.protas.taskmanager.repository;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional <Task> findByUserAndId(User user, Long id);
}
