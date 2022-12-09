package com.example.photographerApp.service;

import com.example.photographerApp.exception.UserNotFoundException;
import com.example.photographerApp.model.Authority;
import com.example.photographerApp.model.User;
import com.example.photographerApp.repository.AuthorityRepository;
import com.example.photographerApp.repository.UserRepository;
import com.example.photographerApp.request.UserCreateRequest;
import com.example.photographerApp.request.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class UserServiceTest
{
    private UserService userService;

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

    @BeforeEach
    public void setUp()
    {
        userRepository = mock(UserRepository.class);
        authorityRepository = mock(AuthorityRepository.class);
        userService = new UserService(userRepository,
                new AuthorityService(authorityRepository),
                new BCryptPasswordEncoder());
    }

    @Test
    void findOneUserById_whenUserIdExists_shouldReturnUser()
    {
        User user = new User(1L,
                "user",
                "password",
                "f_name",
                "l_name",
                new Authority(1L, "admin"),
                null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findOneUserById(1L);

        assertEquals(result,user);
    }

    @Test
    void findOneUserById_whenUserIdDoesNotExists_shouldThrowUserNotFoundException()
    {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findOneUserById(1L));
    }

    @Test
    void findAllUser_whenAnyAmountOfUserExists_shouldReturnUserResponseList()
    {
        List<User> users = new ArrayList<>();
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> expected = userService.getAll();

        assertEquals(expected,users);

        verify(userRepository).findAll();
    }

    @Test
    void createUser_whenCreateUser_shouldReturnUser()
    {
        Authority authority = new Authority(1L, "admin");
        User user = new User(1L,
                "user",
                "password",
                "f_name",
                "l_name",
                authority,
                null);

        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("new user");
        request.setPassword("new password");
        request.setFirstName("new f_name");
        request.setLastName("new l_name");
        request.setAuthorityId(1L);

        when(authorityRepository.findById(1L)).thenReturn(Optional.of(authority));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(request);

        assertEquals(createdUser,user);
    }

    @Test
    void updateUser_whenUserExists_shouldReturnUser()
    {
        User user = new User(1L,
                "user",
                "password",
                "f_name",
                "l_name",
                new Authority(1L, "admin"),
                null);

        User editedUser = new User(1L,
                "new user",
                "new password",
                "new f_name",
                "new l_name",
                new Authority(1L, "admin"),
                null);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail("new user");
        request.setPassword("new password");
        request.setFirstName("new f_name");
        request.setLastName("new l_name");
        request.setAuthorityId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(1L, request);

        verify(userRepository).findById(1L);
        verify(userRepository).save(editedUser);

        assertEquals(updatedUser,editedUser);
    }

    @Test
    void updateUser_whenUserDoesNotExists_shouldThrowUserNotFoundException()
    {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findOneUserById(1L));
    }

    @Test
    void deleteUser_whenUserExists_shouldDeleteUser()
    {
        User user = new User(1L,
                "user",
                "password",
                "f_name",
                "l_name",
                new Authority(1L, "admin"),
                null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteOneUserById(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_whenUserExists_shouldThrowUserNotFoundException()
    {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findOneUserById(1L));
    }
}