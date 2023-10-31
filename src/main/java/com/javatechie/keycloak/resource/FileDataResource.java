package com.javatechie.keycloak.resource;


import com.javatechie.keycloak.domain.file.File;
import com.javatechie.keycloak.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.*;

@RestController
@RequestMapping("/files")
@CrossOrigin
public class FileDataResource {

    @Autowired
    private final FileDataService fileDataService;

    public FileDataResource(FileDataService fileDataService) {
       this.fileDataService = fileDataService;
    }

    @GetMapping("")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<List<File>> getAllFiles() {
        return new ResponseEntity<>(fileDataService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<File> getFileById(@PathVariable("id")long id) {
        return new ResponseEntity<>(fileDataService.findById(id),HttpStatus.OK);

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

        File foundFile = fileDataService.findById(id);
        return new ResponseEntity<>(foundFile,HttpStatus.OK);
    }


}
