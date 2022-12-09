package com.example.photographerApp.service;

import com.example.photographerApp.exception.AuthorityNotFoundException;
import com.example.photographerApp.model.Authority;
import com.example.photographerApp.repository.AuthorityRepository;
import com.example.photographerApp.service.AuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorityServiceTest
{
    private AuthorityService authorityService;

    private AuthorityRepository authorityRepository;

    @BeforeEach
    public void setUp()
    {
        authorityRepository = mock(AuthorityRepository.class);
        authorityService = new AuthorityService(authorityRepository);
    }

    @Test
    void findOneAuthorityById_whenAuthorityIdExists_shouldReturnAuthority()
    {
        Authority authority = new Authority(1L,"Authority");

        when(authorityRepository.findById(1L)).thenReturn(Optional.of(authority));

        Authority result = authorityService.findOneAuthorityById(1L);

        assertEquals(result,authority);
    }

    @Test
    void findOneAuthorityById_whenAuthorityIdDoesNotExists_shouldThrowAuthorityNotFoundException()
    {
        when(authorityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AuthorityNotFoundException.class,
                () -> authorityService.findOneAuthorityById(1L));
    }
}