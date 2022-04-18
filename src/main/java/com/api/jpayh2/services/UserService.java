package com.api.jpayh2.services;

import com.api.jpayh2.entities.User;
import com.api.jpayh2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

//    public List<User> getUsers() {
//        return userRepository.findAll();
//    }
    public Page<User> getUsersPaginado(int page, int size) {
        // paginacion de respuesta
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public Page<String> getUsernames(int page, int size) {
        return userRepository.findAllUsernames(PageRequest.of(page, size));
    }

    public User getUserById(Integer userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("User %d not found", userId)));
    }

    @Cacheable("users")
    public User getUserByUsername(String username) {

        log.info("Metodo: getUserByUsername() | valor capturado: {}", username);

        // simulacion de peticion a BD lenta | 3000 = 3 segundos
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("User %s not found", username)));
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        return userRepository
                .findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("User %s not found", username)));
    }

    // borrar un registro desde la BD y del cache
    @CacheEvict("users")
    public void deleteUserByUsername(String username) {
        User user = getUserByUsername(username);
        userRepository.delete(user);
    }

}











