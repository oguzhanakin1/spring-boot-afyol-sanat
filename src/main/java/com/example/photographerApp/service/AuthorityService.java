package com.example.photographerApp.service;

import com.example.photographerApp.exception.AuthorityNotFoundException;
import com.example.photographerApp.model.Authority;
import com.example.photographerApp.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService
{
    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository)
    {
        this.authorityRepository = authorityRepository;
    }

    public Authority findOneAuthorityById(Long authorityId)
    {
        return authorityRepository.findById(authorityId)
                .orElseThrow(()->
                        new AuthorityNotFoundException("Authority not found with id: "
                                + authorityId));

    }
}
