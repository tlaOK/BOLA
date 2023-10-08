package com.javatechie.keycloak.service;

import com.javatechie.keycloak.domaine.group.Group;
import com.javatechie.keycloak.domaine.user.User;
import com.javatechie.keycloak.domaine.user.Username;
import com.javatechie.keycloak.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public Group findGroupById(long id) {
        return repository.findById(id).orElseThrow(()->new NoSuchElementException("No such Group with the id: "+ id));
    }

    public Group addUser(long id, User user) {
        Group foundGroup = findGroupById(id);
        if(!foundGroup.containsUser(user)) {
            foundGroup.inviteUser(user);
        }
        return foundGroup;
    }
    public Group kickUser(long id, User user) {
        Group foundGroup = findGroupById(id);
        if(foundGroup.containsUser(user)) {
            foundGroup.kickUser(user);
        }

        return foundGroup;
    }
    public boolean groupContainsUser(long id, Username username) {
        Group foundGroup = findGroupById(id);
        return foundGroup.containsUser(username);
    }
}
