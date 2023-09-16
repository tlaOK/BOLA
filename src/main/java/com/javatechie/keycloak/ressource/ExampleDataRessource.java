package com.javatechie.keycloak.ressource;


import com.javatechie.keycloak.domaine.exampledata.Content;
import com.javatechie.keycloak.domaine.exampledata.ExampleData;
import com.javatechie.keycloak.domaine.user.Username;
import com.javatechie.keycloak.service.ExampleDataService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.*;

@RestController
@RequestMapping("/exampledatas")
@CrossOrigin
public class ExampleDataRessource {

    @Autowired
    private final ExampleDataService exampleDataService;

    public ExampleDataRessource(ExampleDataService exampleDataService) {
       this.exampleDataService = exampleDataService;
    }

    @GetMapping("")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<List<ExampleData>> getAllExampleData() {
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(simpleKeycloakAccount.getRoles().contains("admin")) {
            return new ResponseEntity<>(exampleDataService.getAll(), HttpStatus.OK);
        } else if(simpleKeycloakAccount.getRoles().contains("user")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
            Username currentUsername = new Username(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());

            return new ResponseEntity<>(exampleDataService.findExampleDataByCreatorName(currentUsername), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExampleData(@PathVariable("id")long id) {
        exampleDataService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExampleData> updateExampleData(@PathVariable("id")long id, String newContent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        Username currentUsername = new Username(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());

        ExampleData foundExampleData = exampleDataService.findById(id);

        if(foundExampleData.getCreator().getUsername().getValue() == currentUsername.getValue()) {
            foundExampleData.setContent(new Content(newContent));
            foundExampleData = exampleDataService.add(foundExampleData);
            return new ResponseEntity<>(foundExampleData,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }


}
