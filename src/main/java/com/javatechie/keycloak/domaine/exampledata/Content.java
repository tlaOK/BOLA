package com.javatechie.keycloak.domaine.exampledata;


import com.javatechie.keycloak.infrastructure.AbstractSimpleValueObject;

import javax.persistence.Embeddable;


@Embeddable
public class Content extends AbstractSimpleValueObject<String> {

    protected Content() {
        //JPA
    }

    public Content(String value) {
        super(value);
    }
}
