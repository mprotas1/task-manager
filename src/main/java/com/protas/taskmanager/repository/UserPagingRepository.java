package com.protas.taskmanager.repository;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserPagingRepository extends PagingAndSortingRepository<User, Long> {
}
