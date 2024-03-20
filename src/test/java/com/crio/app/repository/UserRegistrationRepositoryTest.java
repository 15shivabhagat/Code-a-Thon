package com.crio.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.crio.app.data.User;

@DataMongoTest
public class UserRegistrationRepositoryTest {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private MongoTemplate entityManager;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                    .id("1")
                    .username("test_user")
                    .score(0)
                    .badges(new HashSet<>())
                    .build();
        entityManager.save(user);
    }

    @Test
    void testSaveUser() {
        User saveUser = userRegistrationRepository.save(user);
        assertNotNull(saveUser);
        assertEquals(saveUser.getId(), "1");
        assertEquals(saveUser.getUsername(), "test_user");

    }

    @Test
    void testExistsByUsername() {
        // entityManager.save(user);
        boolean isExist = userRegistrationRepository.existsByUsername("test_user");
        assertEquals(true, isExist);
    }

    @Test
    void testGetUserFromId() {
        User user = userRegistrationRepository.findById("1").get();
        assertEquals(user.getId(), "1");
        assertEquals(user.getUsername(), "test_user");
    }

    @Test
    void testFindAllByOrderByScoreDesc() {
        List<User> users = userRegistrationRepository.findAllByOrderByScoreDesc();
        assertNotNull(users);
    }

    @Test
    void testDeleteUser() {
        userRegistrationRepository.delete(user);
    }
    
}
