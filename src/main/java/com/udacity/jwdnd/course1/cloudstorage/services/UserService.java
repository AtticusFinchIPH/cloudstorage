package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.dataForms.UserForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private HashService hashService;

    private UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public boolean isUserExisted(UserForm userForm) {
        return (userMapper.getUser(userForm.getUserName()) != null);
    }

    public int createUser(UserForm userForm) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(userForm.getPassword(), encodedSalt);
        User newUser = new User(userForm.getUserName(), encodedSalt, hashedPassword, userForm.getFirstName(), userForm.getLastName());
        return userMapper.insert(newUser);
    }

    public Integer getUserId(String userName) {
        return userMapper.getUser(userName).getUserId();
    }
}
