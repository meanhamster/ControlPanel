package com.sbt.test.repository;

import com.sbt.test.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUserRepository extends JpaRepository<User, Long> {

    Optional<User> getByUsername(String userName);

    void deleteByUsername(String username);

}