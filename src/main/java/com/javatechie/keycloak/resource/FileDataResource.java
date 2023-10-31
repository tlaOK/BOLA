package com.javatechie.keycloak.ressource;


import com.javatechie.keycloak.domaine.file.Content;
import com.javatechie.keycloak.domaine.file.File;
import com.javatechie.keycloak.domaine.user.Username;
import com.javatechie.keycloak.service.FileDataService;
import com.javatechie.keycloak.service.GroupService;
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
@RequestMapping("/files")
@CrossOrigin
public class FileDataRessource {

    @Autowired
    private final FileDataService fileDataService;

    @Autowired
    private final GroupService groupService;

    public FileDataRessource(FileDataService fileDataService, GroupService groupService) {
       this.fileDataService = fileDataService;
       this.groupService = groupService;
    }

    @GetMapping("")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<List<File>> getAllFile() {
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(simpleKeycloakAccount.getRoles().contains("admin")) {
            return new ResponseEntity<>(fileDataService.getAll(), HttpStatus.OK);
        } else if(simpleKeycloakAccount.getRoles().contains("user")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
            Username currentUsername = new Username(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());

            return new ResponseEntity<>(fileDataService.findFileByCreatorName(currentUsername), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<File> getFileById(@PathVariable("id")long id) {
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(simpleKeycloakAccount.getRoles().contains("admin")) {
            return new ResponseEntity<>(fileDataService.findById(id), HttpStatus.OK);
        } else if(simpleKeycloakAccount.getRoles().contains("user")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
            Username currentUsername = new Username(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());
            File currentFile = fileDataService.findById(id);

            return (currentFile.getCreator().getUsername().getValue().equals(currentUsername.getValue()))
                    ? new ResponseEntity<>(currentFile, HttpStatus.OK)
                    : new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }
    @DeleteMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<?> deleteFile(@PathVariable("id")long id) {
        fileDataService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<File> updateFile(@PathVariable("id")long id, @RequestParam("content") String newContent) {

        System.out.println("Hallo");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        Username currentUsername = new Username(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());

        File foundFile = fileDataService.findById(id);

        if(foundFile.getCreator().getUsername().getValue().equals(currentUsername.getValue())) {
            foundFile.setContent(new Content(newContent));
            foundFile = fileDataService.add(foundFile);
            return new ResponseEntity<>(foundFile,HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }


}
