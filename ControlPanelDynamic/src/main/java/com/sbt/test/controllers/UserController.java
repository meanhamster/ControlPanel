package com.sbt.test.controllers;

import com.sbt.test.entities.Privilege;
import com.sbt.test.entities.Role;
import com.sbt.test.entities.User;
import com.sbt.test.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserRepository repo;

    @Autowired
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/get/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> get(@PathVariable("username") String username) {
        try {
            return repo.getByUsername(username)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (RuntimeException e) {
            log.error("Something really bad happened on getUser operation:", e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @PutMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> add(@RequestBody User user) {
        try {
            return ResponseEntity.ok(repo.update(user));
        } catch (RuntimeException e) {
            log.error("Something really bad happened on addUser operation:", e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> delete(@RequestAttribute("username") String username) {
        try {
            repo.deleteByUsername(username);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {
            log.error("Something really bad happened on addUser operation:", e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @PostMapping("/setRoles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> setRoles(@RequestParam("username") String username,
                                         @RequestParam("roles") Set<Role> roles) {
        try {
            Optional<User> userOpt = repo.getByUsername(username);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            User user = userOpt.get();
            user.setRoles(roles);
            repo.update(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {
            log.error("Something really bad happened on addUser operation:", e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }

    @PostMapping("/setAuthorities")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> setAuthorities(@RequestParam("username") String username,
                                               @RequestParam("privileges") Set<Privilege> privileges) {
        try {
            Optional<User> userOpt = repo.getByUsername(username);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            User user = userOpt.get();
            user.setPrivileges(privileges);
            repo.update(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {
            log.error("Something really bad happened on addUser operation:", e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }


}
