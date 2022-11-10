package com.example.photographerApp.service;

import com.example.photographerApp.model.Authority;
import com.example.photographerApp.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements IAuthorityService
{
    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository)
    {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority createAuthority(Authority authority)
    {
        return authorityRepository.save(authority);
    }
}
