package com.javatechie.keycloak.resource;


import com.javatechie.keycloak.domain.user.Password;
import com.javatechie.keycloak.domain.user.User;
import com.javatechie.keycloak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserResource {



    @Autowired
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    @RolesAllowed({"user", "admin"})
    public ResponseEntity<List<User>> allUser(){
        return new ResponseEntity<>(this.userService.getAll(),HttpStatus.OK);

    }


    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<User> findUserById(@PathVariable("id")long id) {
        User foundUser = this.userService.findById(id);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<User> changePassword(@PathVariable("id")long id, @RequestParam("new")String newPassword) {
        User newUser = this.userService.changePasswort(id, new Password(newPassword));
        return new ResponseEntity(newUser, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        this.userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
