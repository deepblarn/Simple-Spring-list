package org.free.proj;

import org.free.proj.entity.Role;
import org.free.proj.entity.User;
import org.free.proj.repository.RoleRepository;
import org.free.proj.repository.UserRepository;
import org.free.proj.service.UserService;
import org.free.proj.storage.StorageProperties;
import org.free.proj.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FreeAplication {

    private String email = "test@gmail.com";
    private String password = "test123";
    private String name = "Test";
    private String lastname = "Admin";


    public static void main(String[] args) {
        SpringApplication.run(FreeAplication.class, args);
    }

    @Bean
    public CommandLineRunner createData(UserRepository userRepository, RoleRepository roleRepository, UserService userService, StorageService storageService) {
        return args -> {
            Role role1 = new Role();
            role1.setId(1);
            role1.setRole("ADMIN");
            roleRepository.save(role1);
            Role role2 = new Role();
            role2.setId(2);
            role2.setRole("USER");
            roleRepository.save(role2);

            if (userRepository.findByEmail(this.email) == null){
                User user = new User();
                user.setRole(role1);
                user.setPassword(userService.passwordEncoder.encode(this.password));
                user.setEmail(this.email);
                user.setName(this.name);
                user.setActive(1);
                user.setLastname(this.lastname);
                userRepository.save(user);
            }




        };
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

}