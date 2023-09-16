package com.javatechie.keycloak.service;

import com.javatechie.keycloak.domaine.exampledata.Content;
import com.javatechie.keycloak.domaine.exampledata.ExampleData;
import com.javatechie.keycloak.domaine.user.Password;
import com.javatechie.keycloak.domaine.user.User;
import com.javatechie.keycloak.domaine.user.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class postData {

    @Autowired
    private final ExampleDataService exampleDataService;

    @Autowired
    private final UserService userService;

    public postData(ExampleDataService exampleDataService, UserService userService) {
        this.exampleDataService = exampleDataService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        ExampleData exampleData1 = new ExampleData(new Content("Dieser Inhalt sollte nur von dem Ersteller zu sehen sein"));
        User tla = new User(new Username("tla"), new Password("Password"));
        exampleData1.setCreator(tla);
        User arne = new User(new Username("arne"), new Password("password"));

        userService.add(tla);
        userService.add(arne);
        exampleDataService.add(exampleData1);
    }
}
