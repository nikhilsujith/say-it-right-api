package com.nikhilsujith.sayitrightapi.service;

import com.nikhilsujith.sayitrightapi.model.User;
import com.nikhilsujith.sayitrightapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {

//    Get Repository
    @Autowired
    UserRepository repository;

//    Get all user data
    public List<User> getAllUserData(){
        return repository.findAll();
    }

//    Get user by UserId
    public Optional<User> getUserById(String userId){
        return repository.findById(userId);
    }

}
