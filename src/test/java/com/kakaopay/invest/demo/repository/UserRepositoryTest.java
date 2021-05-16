package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Repository]사용자_레퍼지토리_테스트")
@SpringBootTest
class UserRepositoryTest {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void findById() {
        assertThat(userRepository.findById(1001L)).isNotEmpty();
    }

    @Test
    void findAll() {
        assertThat(userRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(User.class);
    }
}