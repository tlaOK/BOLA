package com.javatechie.keycloak.service;


import com.javatechie.keycloak.domain.user.Password;
import com.javatechie.keycloak.domain.user.User;
import com.javatechie.keycloak.domain.user.Username;
import com.javatechie.keycloak.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private final UserRepository repository;

    public UserService(UserRepository repository)  {
        this.repository = repository;
    }

    public User add(User user) {
        return repository.save(user);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User findById(long id) {
        return repository.findById(id).orElseThrow(()->new NoSuchElementException("No such User with the id: "+ id));
    }

    public User changePasswort(long id, Password password) {
        User foundUser = this.findById(id);
        foundUser.setPassword(password);
        return repository.save(foundUser);
    }
    public List<User> findAllUserByUsername(Username username) {
        return this.repository.findAllUserByUsername(username);
    }
}
