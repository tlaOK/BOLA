package com.javatechie.keycloak.ressource;


import com.javatechie.keycloak.domaine.file.Content;
import com.javatechie.keycloak.domaine.file.File;
import com.javatechie.keycloak.domaine.user.Username;
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
@RequestMapping("/files")
@CrossOrigin
public class FileDataRessource {

    @Autowired
    private final FileDataService fileDataService;

    public FileDataRessource(FileDataService fileDataService) {
       this.fileDataService = fileDataService;
    }

    @GetMapping("")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<List<File>> getAllExampleData() {
        return new ResponseEntity<>(fileDataService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<File> getExampleDataById(@PathVariable("id")long id) {
        return new ResponseEntity<>(fileDataService.findById(id),HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<?> deleteExampleData(@PathVariable("id")long id) {
        fileDataService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<File> updateExampleData(@PathVariable("id")long id, @RequestParam("content") String newContent) {

        File foundFile = fileDataService.findById(id);
        return new ResponseEntity<>(foundFile,HttpStatus.OK);
    }


}
