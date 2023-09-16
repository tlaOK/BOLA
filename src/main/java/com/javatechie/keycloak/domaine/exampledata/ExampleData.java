package com.javatechie.keycloak.domaine.exampledata;


import com.javatechie.keycloak.domaine.user.User;
import com.javatechie.keycloak.infrastructure.AbstractEntity;

import javax.persistence.*;


@Entity
@Table(name = "TAB_EXAMPLEDATA")
public class ExampleData extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAMPLEDATA_ID", unique = true, nullable = false)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "EXAMPLEDATA_SHORT"))
    private Content content;

    @ManyToOne
    private User creator;

    protected ExampleData() {
        //JPA
    }

    public ExampleData(Content content) {
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
}
