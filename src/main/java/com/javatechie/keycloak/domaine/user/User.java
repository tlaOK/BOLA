package com.javatechie.keycloak.domaine.user;


import com.javatechie.keycloak.infrastructure.AbstractEntity;

import javax.persistence.*;


@Entity
@Table(name = "TAB_USER")
public class User extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false, unique = true)
    private long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "USER_USERNAME"))
    private Username username;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "USER_PASSWORD"))
    private Password password;


    protected User() {
        //JPA
    }

    public User(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
    public void setUsername(Username username) {
        this.username = username;
    }
}
