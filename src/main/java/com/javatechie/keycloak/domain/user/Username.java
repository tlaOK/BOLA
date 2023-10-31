package com.javatechie.keycloak.domain.user;


import com.javatechie.keycloak.infrastructure.AbstractSimpleValueObject;

import javax.persistence.Embeddable;


@Embeddable
public class Username extends AbstractSimpleValueObject<String> {

    protected Username() {
        //JPA
    }

    public Username(String value) {
        super(value);
    }
}
