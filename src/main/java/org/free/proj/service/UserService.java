package org.free.proj.service;

import org.free.proj.entity.User;
import org.free.proj.repository.RoleRepository;
import org.free.proj.repository.UserRepository;
import org.free.proj.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);

        Role userRole = roleRepository.findByRole("USER");
        user.setRole(userRole);

        this.userRepository.save(user);
    }


    public ArrayList<User> All(){
        return this.userRepository.findAll();
    }
}
