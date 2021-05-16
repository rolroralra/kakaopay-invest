package com.kakaopay.invest.demo.repository.impl;

import com.kakaopay.invest.demo.model.User;
import com.kakaopay.invest.demo.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super();

        map.put(1001L, new User(1001L, "root", "root@kakao.com"));
        map.put(1002L, new User(1002L, "admin", "admin@kakao.com"));
        map.put(1003L, new User(1003L, "rolroralra", "rolroralra@naver.com"));
        map.put(1004L, new User(1004L, "guest", "guest@kakao.com"));
    }

    @Override
    public Optional<User> findById(Long id) {
        return selectById(id);
    }

    @Override
    public List<User> findAll() {
        return selectAll();
    }
}
