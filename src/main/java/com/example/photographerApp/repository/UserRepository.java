package com.example.photographerApp.repository;

import com.example.photographerApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User,Long>
{
    @Query("SELECT u from User u where u.email = :email")
    User findByEmail(@Param("email") String email);
}
