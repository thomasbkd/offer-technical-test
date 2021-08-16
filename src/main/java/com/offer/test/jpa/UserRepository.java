package com.offer.test.jpa;

import com.offer.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUsername(String username);

}
