package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        userRepository.save(new User(1L, "root", "root@kakao.com"));
        userRepository.save(new User(2L, "admin", "admin@kakao.com"));
        userRepository.save(new User(3L, "rolroralra", "rolroralra@naver.com"));
        userRepository.save(new User(4L, "guest", "guest@kakao.com"));
    }

    @DisplayName("특정_사용자_ID_조회_테스트")
    @Test
    void findById() {
        assertThat(userRepository.findById(1L)).isNotEmpty();
    }

    @DisplayName("사용자_전체목록_조회_테스트")
    @Test
    void findAll() {
        assertThat(userRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(User.class);
    }

    @DisplayName("사용자_INSERT_테스트")
    @Test
    public void insert() {
        User user = new User("test_user_id", "test@kakao.com");
        userRepository.save(user);
        assertThat(userRepository.findById(user.getId()))
                .isNotEmpty()
                .hasValue(user);
    }

    @DisplayName("사용자_UPDATE_테스트")
    @Test
    public void update() {
        User user = userRepository.findAll().stream().findAny().orElse(null);
        assertThat(user).isNotNull();

        user.setName(user.getEmail() + "," + user.getName() + user.getId() + "@kakao.com");
        userRepository.save(user);

        assertThat(userRepository.findById(user.getId()))
                .isNotEmpty()
                .hasValue(user);
    }

    @DisplayName("사용자_DELETE_테스트")
    @Test
    public void delete() {
        User user = new User("test_user_id", "test@kakao.com");
        userRepository.save(user);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();

        userRepository.deleteById(user.getId());

        assertThat(userRepository.findById(user.getId()))
                .isEmpty();

        assertThat(userRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(User.class)
                .doesNotContain(user);
    }

}