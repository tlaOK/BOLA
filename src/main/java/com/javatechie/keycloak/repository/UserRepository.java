package com.javatechie.keycloak.repository;


import com.javatechie.keycloak.domain.user.User;
import com.javatechie.keycloak.domain.user.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    List<User> findAllUserByUsername(Username username);
}
