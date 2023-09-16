package com.javatechie.keycloak.repository;

import com.javatechie.keycloak.domaine.exampledata.ExampleData;
import com.javatechie.keycloak.domaine.user.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExampleDataRepository extends JpaRepository<ExampleData, Long> {

    @Query("SELECT ex FROM ExampleData ex WHERE ex.creator.username = ?1")
    List<ExampleData> findAllByCreator(Username username);

}
