package com.hanyang.service;

import com.hanyang.pojo.UserInfo;
import com.hanyang.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String login(String account, String password) {
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account, password);
        if (userInfo == null) {
            return "account / password ERROR!";
        }
        return "Login Success";
    }

    public String register(UserInfo userInfo) {
        if (checkUserExists(userInfo.getUserName())){
            throw new RuntimeException("User already registered.");
        }
        userInfo.setCreateDate(new Date());

        userRepository.save(userInfo);
        return "Register Success!";
    }

    public boolean checkUserExists(String userName){
        UserInfo user = userRepository.findByUserName(userName);
        if (user == null){
            return false;
        }
        return true;
    }

}
