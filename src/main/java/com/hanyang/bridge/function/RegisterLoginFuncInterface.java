package com.hanyang.bridge.function;

import com.hanyang.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

public interface RegisterLoginFuncInterface {
    public String login(String account, String password);
    public String register(UserInfo userInfo);
    public boolean checkUserExists(String userName);
    // 第三方账号登录接口
    public String login3rd(HttpServletRequest request);
}
