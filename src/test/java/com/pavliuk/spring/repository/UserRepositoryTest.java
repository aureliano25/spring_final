package com.pavliuk.spring.repository;

import com.pavliuk.spring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setLogin("petro");
        user.setPassword("petro");
        user.setFirstName("petro");
        user.setLastName("petro");
        user.setRoleId((long)2);

        User savedUser = userRepository.save(user);
        User userFromDb = entityManager.find(User.class, savedUser.getId());
        assertThat(savedUser.getFirstName()).isEqualTo(userFromDb.getFirstName());
    }
}