package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
