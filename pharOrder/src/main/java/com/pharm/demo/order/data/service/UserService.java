package com.pharm.demo.order.data.service;

import com.pharm.demo.order.data.model.User;
import com.pharm.demo.order.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
}
