package com.javatechie.keycloak.resource;


import com.javatechie.keycloak.domain.user.Password;
import com.javatechie.keycloak.domain.user.User;
import com.javatechie.keycloak.domain.user.Username;
import com.javatechie.keycloak.service.UserService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) SecurityContextHolder.getContext().getAuthentication().getDetails();

        if(simpleKeycloakAccount.getRoles().contains("admin")) {
            return new ResponseEntity<>(this.userService.getAll(),HttpStatus.OK);
        } else if(simpleKeycloakAccount.getRoles().contains("user")) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
            Username currentUsername = new Username(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());

            return new ResponseEntity<>(this.userService.findAllUserByUsername(currentUsername), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
        }


    }


    @GetMapping("/{id}")
    @RolesAllowed("user")
    public ResponseEntity<User> findUserById(@PathVariable("id")long id) {

        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        //Username currentUsername = new Username(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());

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
