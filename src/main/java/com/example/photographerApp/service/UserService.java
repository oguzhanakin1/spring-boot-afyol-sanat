package com.example.photographerApp.service;

import com.example.photographerApp.exception.UserNotFoundException;
import com.example.photographerApp.model.User;
import com.example.photographerApp.repository.UserRepository;
import com.example.photographerApp.request.UserCreateRequest;
import com.example.photographerApp.request.UserUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService
{
    private final UserRepository userRepository;

    private final AuthorityService authorityService;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AuthorityService authorityService,
                       PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserCreateRequest request)
    {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserCreateTime(LocalDateTime.now());
        user.setAuthority(authorityService.findOneAuthorityById(request.getAuthorityId()));

        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserUpdateRequest request)
    {
        User userToEdit = userRepository.findById(userId).orElseThrow();

        userToEdit.setEmail(request.getEmail());
        userToEdit.setFirstName(request.getFirstName());
        userToEdit.setLastName(request.getLastName());
        userToEdit.setPassword(request.getPassword());

        return userRepository.save(userToEdit);
    }

    public void deleteOneUserById(Long userId)
    {
        userRepository.deleteById(userId);
    }

    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    public User findOneUserById(Long userId)
    {
        return userRepository.findById(userId)
                .orElseThrow(()->
                        new UserNotFoundException
                                ("User not found with id: " + userId));
    }

    public User findOneUserByEmail(String email)
    {
        return userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UserNotFoundException
                                ("User not found with email: " + email));

    }
}
