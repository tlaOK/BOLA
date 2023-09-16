package com.javatechie.keycloak.domaine.user;


import com.javatechie.keycloak.infrastructure.AbstractSimpleValueObject;

import javax.persistence.Embeddable;


@Embeddable
public class Password extends AbstractSimpleValueObject<String> {

    protected Password() {
        //JPA
    }

    public Password(String value) {
        super(value);
    }
}
