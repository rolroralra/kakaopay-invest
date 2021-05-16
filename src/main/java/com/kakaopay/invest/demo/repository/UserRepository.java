package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    List<User> findAll();
}
