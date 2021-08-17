package com.offer.test.jpa;

import com.offer.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This interface provides all the needed methods to interact with the data source, especially via its mother interface
 * <code>JpaRepository</code>.
 * @see JpaRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Gets all the users in the data source with the same username as given, and returns them in a list.
     * @param username the username to find.
     * @return a list of the users in the data source with the same username as given.
     * @see JpaRepository
     */
    List<User> findAllByUsername(String username);
}
