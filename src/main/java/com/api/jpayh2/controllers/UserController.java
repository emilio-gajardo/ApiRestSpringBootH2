package com.api.jpayh2.controllers;

import com.api.jpayh2.entities.User;
import com.api.jpayh2.services.UserService;
import io.micrometer.core.annotation.Timed;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /* endpoint basico que retorna todos los registros sin paginación
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }*/
    // endpoint complejo que retorna todos los registros o paginar si se entregan los parametros page y size 
    @GetMapping
    @Timed("get.users")
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        return new ResponseEntity<>(userService.getUsersPaginado(page, size), HttpStatus.OK);
    }

    @GetMapping("/usernames")
    public ResponseEntity<Page<String>> getUsernames(
            @RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        return new ResponseEntity<>(userService.getUsernames(page, size), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/v2/")
    public ResponseEntity<User> getUserById2(@PathParam("userId") Integer userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> authenticate(@RequestBody User user) {
        return new ResponseEntity<>(
                userService.getUserByUsernameAndPassword(
                        user.getUsername(),
                        user.getPassword()),
                HttpStatus.OK);
    }

    
    // endpoint para eliminar un registro
    @DeleteMapping("/deleteUserByUsername/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}









