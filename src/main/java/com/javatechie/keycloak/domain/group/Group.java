package com.javatechie.keycloak.domain.group;

import com.javatechie.keycloak.domain.user.User;
import com.javatechie.keycloak.domain.user.Username;

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

    public Group() {
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
