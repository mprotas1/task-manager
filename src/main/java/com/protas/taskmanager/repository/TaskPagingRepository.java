package com.protas.taskmanager.repository;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TaskPagingRepository extends PagingAndSortingRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
    Page<Task> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
