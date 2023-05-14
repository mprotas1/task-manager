package com.protas.taskmanager.service;

import com.protas.taskmanager.entity.User;
import com.protas.taskmanager.repository.UserPagingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserPagingService {
    private final UserPagingRepository userPagingRepository;

    private final int pageSize = 20;

    public UserPagingService(UserPagingRepository userPagingRepository) {
        this.userPagingRepository = userPagingRepository;
    }

    // get Page<User> by Integer 'page'
    public Page<User> getAllUsersByPage(Integer page) {

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return userPagingRepository
                .findAll(pageRequest);
    }

    // get Page<User> sorted by Integer 'page'
    public Page<User> getAllUsersByPageSorted(Integer page) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, pageSize)
                .withSort(sort);
        return userPagingRepository
                .findAll(pageRequest);
    }
}
