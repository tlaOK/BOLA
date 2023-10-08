package com.javatechie.keycloak.repository;

import com.javatechie.keycloak.domaine.file.File;
import com.javatechie.keycloak.domaine.user.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDataRepository extends JpaRepository<File, Long> {

    @Query("SELECT f FROM File f WHERE f.creator.username = ?1")
    List<File> findAllByCreator(Username username);

}
