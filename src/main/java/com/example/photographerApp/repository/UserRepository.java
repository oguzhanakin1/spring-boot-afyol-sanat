package com.example.photographerApp.repository;

import com.example.photographerApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long>
{
    @Query("SELECT u from User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    void removeByEmail(String email);
}
