package com.protas.taskmanager.repository;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional <Task> findByUserAndId(User user, Long id);
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
    Page<Task> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
