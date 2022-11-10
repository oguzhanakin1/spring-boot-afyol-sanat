package com.example.photographerApp.service;

import com.example.photographerApp.model.User;
import com.example.photographerApp.repository.AuthorityRepository;
import com.example.photographerApp.repository.UserRepository;
import com.example.photographerApp.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService
{
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User createUser(UserRequest request)
    {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserCreateTime(LocalDateTime.now());
        user.setAuthority(authorityRepository.findById(request.getAuthorityId())
                .orElseThrow(()-> new RuntimeException("hata")));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, UserRequest request)
    {
        User userToEdit = userRepository.findById(userId).orElseThrow();

        userToEdit.setEmail(request.getEmail());
        userToEdit.setFirstName(request.getFirstName());
        userToEdit.setLastName(request.getLastName());
        userToEdit.setPassword(request.getPassword());

        return userRepository.save(userToEdit);
    }

    @Override
    public void deleteOneUserById(Long userId)
    {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findOneUserById(Long userId)
    {
        return userRepository.findById(userId);
    }
    @Override
    public User findOneUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
}
