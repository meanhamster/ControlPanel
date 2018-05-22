package com.sbt.test.repository;

import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.persistence.EntityNotFoundException;

public class UserNotExistException extends JpaObjectRetrievalFailureException {
    UserNotExistException(String message) {
        super(new EntityNotFoundException(message));
    }
}
