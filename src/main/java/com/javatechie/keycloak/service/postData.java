package com.javatechie.keycloak.service;

import com.javatechie.keycloak.domaine.file.Content;
import com.javatechie.keycloak.domaine.file.File;
import com.javatechie.keycloak.domaine.user.Password;
import com.javatechie.keycloak.domaine.user.User;
import com.javatechie.keycloak.domaine.user.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class postData {

    @Autowired
    private final FileDataService fileDataService;

    @Autowired
    private final UserService userService;

    public postData(FileDataService fileDataService, UserService userService) {
        this.fileDataService = fileDataService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        File file1 = new File(new Content("Dieser Inhalt sollte nur von dem Ersteller zu sehen sein"));
        User tla = new User(new Username("tla"), new Password("Password"));
        file1.setCreator(tla);
        User arne = new User(new Username("arne"), new Password("password"));

        userService.add(tla);
        userService.add(arne);
        fileDataService.add(file1);
    }
}
