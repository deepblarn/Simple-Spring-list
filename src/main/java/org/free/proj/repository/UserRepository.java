package org.free.proj.repository;

import org.free.proj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Long> {
    ArrayList<User> findAll();
    User findById(int id);
    User findByEmail(String email);
}
