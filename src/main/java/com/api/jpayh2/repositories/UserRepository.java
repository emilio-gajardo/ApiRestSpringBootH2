package com.api.jpayh2.repositories;

import org.springframework.stereotype.Repository;

import com.api.jpayh2.entities.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Query method de Spring Data
    public Optional<User> findByUsername(String username);
    
    // Query method de Spring Data
    public Optional<User> findByUsernameAndPassword(String username, String password);
    
    // Custom Query
    // No es SQL, es JPQL = Java persistence query language
    // User = es el nombre de la clase declarada en el package "entities"
    @Query("SELECT u.username FROM User u")
    public Page<String> findAllUsernames(Pageable pageable);
    
}


