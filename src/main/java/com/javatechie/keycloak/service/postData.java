package com.javatechie.keycloak.service;

import com.javatechie.keycloak.domain.file.Content;
import com.javatechie.keycloak.domain.file.File;
import com.javatechie.keycloak.domain.group.Group;
import com.javatechie.keycloak.domain.user.Password;
import com.javatechie.keycloak.domain.user.User;
import com.javatechie.keycloak.domain.user.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class postData {

    @Autowired
    private final GroupService groupService;

    @Autowired
    private final FileDataService fileDataService;

    @Autowired
    private final UserService userService;

    public postData(FileDataService fileDataService, UserService userService, GroupService groupService) {
        this.fileDataService = fileDataService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @PostConstruct
    public void init() {
        File file1 = new File(new Content("Dieser Inhalt sollte nur von tla zu sehen sein"));
        File file2 = new File(new Content("Dieser Inhalt sollte nur von arne zu sehen sein"));
        File file3 = new File(new Content("Dieser Inhalt sollte von Gruppenteilnehmern zu sehen sein"));
        Group group = new Group();
        User tla = new User(new Username("tla"), new Password("Password"));
        file1.setCreator(tla);
        User nils = new User(new Username("Nils"), new Password("password"));
        file3.setCreator(nils);
        group.inviteUser(tla);
        group.inviteUser(nils);
        User arne = new User(new Username("arne"), new Password("password"));
        file2.setCreator(arne);
        userService.add(tla);
        userService.add(arne);
        userService.add(nils);
        group = groupService.addGroup(group);
        file3.addGroup(group);
        fileDataService.add(file1);
        fileDataService.add(file2);
        fileDataService.add(file3);
    }
}