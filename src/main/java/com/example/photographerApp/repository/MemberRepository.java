package com.example.photographerApp.repository;

import com.example.photographerApp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long>
{
}
