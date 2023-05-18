package com.protas.taskmanager.service;

import com.protas.taskmanager.entity.Task;
import com.protas.taskmanager.repository.TaskPagingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskPagingService {

    private final TaskPagingRepository taskPagingRepository;

    private final int pageSize = 20;

    public Page<Task> getAllTasks(Long userId, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return taskPagingRepository
                .findByUserId(userId, pageRequest);
    }

    public Page<Task> getAllTasksSorted(Long userId, Integer page) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");
        PageRequest pageRequest = PageRequest.of(page, pageSize).withSort(sort);
        return taskPagingRepository
                .findByUserId(userId, pageRequest);
    }
}
