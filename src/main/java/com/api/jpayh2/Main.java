package com.api.jpayh2;

import com.api.jpayh2.entities.Role;
import com.api.jpayh2.entities.User;
import com.api.jpayh2.entities.UserInRole;
import com.api.jpayh2.repositories.RoleRepository;
import com.api.jpayh2.repositories.UserInRoleRepository;
import com.api.jpayh2.repositories.UserRepository;
import com.github.javafaker.Faker;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Ejecucion del sistema
@SpringBootApplication
public class Main implements ApplicationRunner {

    @Autowired
    private Faker faker;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Poblando la BD H2 para hacer las pruebas
    @Override
    public void run(ApplicationArguments args) throws Exception {

        Role roles[] = {
            new Role("ADMIN"),
            new Role("SUPPORT"),
            new Role("USER")
        };

        // agregando los roles a la BD H2
        for (Role role : roles) {
            roleRepository.save(role);
        }

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername(faker.name().username());
            user.setPassword(faker.dragonBall().character());
            User userCreated = repository.save(user);
            UserInRole userInRole = new UserInRole(userCreated, roles[new Random().nextInt(3)]);

            log.info("User created | username: {} | password: {} | role: {}",
                    userCreated.getUsername(),
                    userCreated.getPassword(),
                    userInRole.getRole().getName());

            userInRoleRepository.save(userInRole);
        }
    }

}
