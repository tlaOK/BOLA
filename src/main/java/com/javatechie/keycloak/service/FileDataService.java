package com.javatechie.keycloak.service;

import com.javatechie.keycloak.domain.file.File;
import com.javatechie.keycloak.domain.user.Username;
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
        return repository.findById(id).orElseThrow(()->new NoSuchElementException("No such File with the id: "+ id));
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }


    public List<File> findFileByCreatorName(Username username) {
        return this.repository.findAllByCreator(username);
    }
}
