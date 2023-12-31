package com.javatechie.keycloak.service;

import com.javatechie.keycloak.domaine.file.File;
import com.javatechie.keycloak.domaine.user.Username;
import com.javatechie.keycloak.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FileDataService {

    @Autowired
    private final FileDataRepository repository;

    public FileDataService(FileDataRepository repository) {
        this.repository = repository;
    }

    public File add(File file) {
        return this.repository.save(file);
    }

    public List<File> getAll() {
        return repository.findAll();
    }

    public File findById(long id) {
        return repository.findById(id).orElseThrow(()->new NoSuchElementException("No such User with the id: "+ id));
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }


    public List<File> findExampleDataByCreatorName(Username username) {
        return this.repository.findAllByCreator(username);
    }
}
