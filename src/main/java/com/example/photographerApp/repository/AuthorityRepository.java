package com.example.photographerApp.repository;

import com.example.photographerApp.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long>
{
}
