package com.crio.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.crio.app.data.User;

@Repository
public interface UserRegistrationRepository extends MongoRepository<User, String> {

    boolean existsByUsername(String username);

    List<User> findAllByOrderByScoreDesc();
    
}
