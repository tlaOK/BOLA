package com.javatechie.keycloak.service;

import com.javatechie.keycloak.domain.file.Content;
import com.javatechie.keycloak.domain.file.File;
import com.javatechie.keycloak.domain.user.Password;
import com.javatechie.keycloak.domain.user.User;
import com.javatechie.keycloak.domain.user.Username;
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
        File file1 = new File(new Content("Dieser Inhalt sollte nur von tla zu sehen sein"));
        File file2 = new File(new Content("Dieser Inhalt sollte nur von arne zu sehen sein"));
        User tla = new User(new Username("tla"), new Password("Password"));
        file1.setCreator(tla);

        User arne = new User(new Username("arne"), new Password("password"));
        file2.setCreator(arne);
        userService.add(tla);
        userService.add(arne);
        fileDataService.add(file1);
        fileDataService.add(file2);
    }
}
