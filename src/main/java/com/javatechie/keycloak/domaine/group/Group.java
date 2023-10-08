package com.javatechie.keycloak.domaine.group;

import com.javatechie.keycloak.domaine.user.User;
import com.javatechie.keycloak.domaine.user.Username;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GROUPS")
public class Group {

    @Id
    private long id;

    @ManyToMany
    private List<User> participants = new ArrayList<>();

    protected Group() {
        //JPA
    }

    public void inviteUser(User user) {
        participants.add(user);
    }

    public void kickUser(User user) {
        if (participants.contains(user)) {
            participants.remove(user);
        }

    }

    public boolean containsUser(User user) {
        return this.participants.contains(user);
    }

    public boolean containsUser(Username username) {
        boolean valid = false;

        for(int i = 0; i < participants.size() && !valid; i++) {
            valid = participants.get(i).getUsername().getValue().equals(username.getValue());
        }

        return valid;
    }
}

