package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.User;
import com.kakaopay.invest.demo.repository.UserRepository;
import com.kakaopay.invest.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
