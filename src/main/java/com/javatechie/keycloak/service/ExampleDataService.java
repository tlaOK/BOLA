package com.javatechie.keycloak.service;

import com.javatechie.keycloak.domaine.exampledata.ExampleData;
import com.javatechie.keycloak.domaine.user.User;
import com.javatechie.keycloak.domaine.user.Username;
import com.javatechie.keycloak.repository.ExampleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ExampleDataService {

    @Autowired
    private final ExampleDataRepository repository;

    public ExampleDataService(ExampleDataRepository repository) {
        this.repository = repository;
    }

    public ExampleData add(ExampleData exampleData) {
        return this.repository.save(exampleData);
    }

    public List<ExampleData> getAll() {
        return repository.findAll();
    }

    public ExampleData findById(long id) {
        return repository.findById(id).orElseThrow(()->new NoSuchElementException("No such User with the id: "+ id));
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }


    public List<ExampleData> findExampleDataByCreatorName(Username username) {
        return this.repository.findAllByCreator(username);
    }
}
