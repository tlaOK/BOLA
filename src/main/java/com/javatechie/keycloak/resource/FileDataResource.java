package com.javatechie.keycloak.resource;


import com.javatechie.keycloak.domain.file.Content;
import com.javatechie.keycloak.domain.file.File;
import com.javatechie.keycloak.domain.user.Username;
import com.javatechie.keycloak.service.FileDataService;
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
public class FileDataResource {

    @Autowired
    private final FileDataService fileDataService;

    public FileDataResource(FileDataService fileDataService) {
       this.fileDataService = fileDataService;
    }

    @GetMapping("")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<List<File>> getAllExampleData() {
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(simpleKeycloakAccount.getRoles().contains("admin")) {
            return new ResponseEntity<>(fileDataService.getAll(), HttpStatus.OK);
        } else if(simpleKeycloakAccount.getRoles().contains("user")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
            Username currentUsername = new Username(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());

            return new ResponseEntity<>(fileDataService.findExampleDataByCreatorName(currentUsername), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExampleData(@PathVariable("id")long id) {
        fileDataService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<File> updateExampleData(@PathVariable("id")long id, String newContent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        Username currentUsername = new Username(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());

        File foundFile = fileDataService.findById(id);

        if(foundFile.getCreator().getUsername().getValue() == currentUsername.getValue()) {
            foundFile.setContent(new Content(newContent));
            foundFile = fileDataService.add(foundFile);
            return new ResponseEntity<>(foundFile,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }


}
