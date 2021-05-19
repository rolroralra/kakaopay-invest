package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User findById(Long userId);

    Page<User> findAll(Pageable pageable);
}
