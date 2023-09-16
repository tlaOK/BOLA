package com.javatechie.keycloak.domaine.group;

import com.javatechie.keycloak.domaine.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GROUPS")
public class Group {

    @Id
    private long id;

    @OneToMany
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
}
