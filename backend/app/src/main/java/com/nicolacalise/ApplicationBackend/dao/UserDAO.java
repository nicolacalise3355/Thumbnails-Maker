package com.nicolacalise.ApplicationBackend.dao;

import com.nicolacalise.ApplicationBackend.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
}
