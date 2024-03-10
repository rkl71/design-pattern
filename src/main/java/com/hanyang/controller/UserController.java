package com.hanyang.controller;

import com.hanyang.adapter.Login3rdAdapter;
import com.hanyang.pojo.UserInfo;
import com.hanyang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Login3rdAdapter login3rdAdapter;

    @PostMapping("/login")
    public String login(String account, String password) {
        return userService.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo) {
        return userService.register(userInfo);
    }

    @GetMapping("/gitee")
    public String gitee(String code, String state) throws IOException {
        return login3rdAdapter.loginByGitee(code, state);
    }
}
