package com.api.jpayh2.config;

import com.api.jpayh2.entities.User;
import com.api.jpayh2.entities.UserInRole;
import com.api.jpayh2.repositories.UserInRoleRepository;
import com.api.jpayh2.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optional = userRepository.findByUsername(username);

        if (optional.isPresent()) {
            User user = optional.get();
            List<UserInRole> userInRoles = userInRoleRepository.findByUser(user);
            String[] roles = userInRoles
                    .stream()
                    .map(r -> r.getRole().getName())
                    .toArray(String[]::new);
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .roles(roles).build();
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
    }
}