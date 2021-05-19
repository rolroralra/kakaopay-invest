package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Repository]사용자_레퍼지토리_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@SpringBootTest
class UserRepositoryTest {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DisplayName("테스트01_정상_특정_사용자_ID_조회")
    @ParameterizedTest
    @ValueSource(longs = {1,2,3,4,5,6})
    void findById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        assertThat(user)
                .isNotNull()
                .isInstanceOf(User.class);

        assertThat(user.getId())
                .isEqualTo(userId);
    }

    @DisplayName("테스트02_정상_사용자_전체목록_조회")
    @Test
    void 테스트02_정상_사용자_전체목록_조회() {
        assertThat(userRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(User.class);
    }

    @DisplayName("테스트03_정상_사용자_INSERT")
    @ParameterizedTest
    @CsvSource(value = {"testUser:test@kakao.com"}, delimiterString = ":")
    public void 테스트03_정상_사용자_INSERT(String userName, String userEmail) {
        User user = new User(userName, userEmail);

        userRepository.save(user);

        assertThat(userRepository.findById(user.getId()))
                .isNotEmpty()
                .hasValue(user);
    }

    @DisplayName("테스트04_정상_사용자_UPDATE")
    @Test
    public void 테스트04_정상_사용자_UPDATE() {
        User user = userRepository.findAll().stream().findAny().orElse(null);
        assertThat(user).isNotNull();

        user.setName(user.getEmail() + "," + user.getName() + user.getId() + "@kakao.com");
        userRepository.save(user);

        assertThat(userRepository.findById(user.getId()))
                .isNotEmpty()
                .hasValue(user);
    }

    @DisplayName("테스트05_정상_사용자_DELETE")
    @Test
    public void 테스트05_정상_사용자_DELETE() {
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