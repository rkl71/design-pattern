package com.hanyang.bridge.function;

import com.hanyang.pojo.UserInfo;
import com.hanyang.repo.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public abstract class AbstractRegisterLoginFunc implements RegisterLoginFuncInterface {
    protected String commonLogin(String account, String password, UserRepository userRepository) {
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account, password);
        if (userInfo == null) {
            return "account / password ERROR!";
        }
        return "Login Success";
    }

    protected String commonRegister(UserInfo userInfo, UserRepository userRepository) {
        if (commonCheckUserExists(userInfo.getUserName(), userRepository)) {
            throw new RuntimeException("User already registered.");
        }
        userInfo.setCreateDate(new Date());
        userRepository.save(userInfo);
        return "Register Success!";
    }

    protected boolean commonCheckUserExists(String userName, UserRepository userRepository) {
        UserInfo user = userRepository.findByUserName(userName);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public String login(String account, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String register(UserInfo userInfo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkUserExists(String userName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }
}
