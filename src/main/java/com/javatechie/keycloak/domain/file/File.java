package com.javatechie.keycloak.domain.file;


import com.javatechie.keycloak.domain.group.Group;
import com.javatechie.keycloak.domain.user.User;
import com.javatechie.keycloak.domain.user.Username;
import com.javatechie.keycloak.infrastructure.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "TAB_EXAMPLEDATA")
public class File extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAMPLEDATA_ID", unique = true, nullable = false)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "EXAMPLEDATA_SHORT"))
    private Content content;

    @ManyToOne
    private User creator;

    @ManyToMany
    private List<Group> groups = new ArrayList<>();

    protected File() {
        //JPA
    }

    public File(Content content) {
        this.content = content;
    }


    @Override
    public Long getId() {
        return this.id;
    }


    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public boolean groupsContainsUser(Username username) {
        boolean valid = false;

        for (Group cur : groups) {
            valid = cur.containsUser(username);
        }

        return valid;
    }
}