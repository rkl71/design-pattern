package com.hanyang.bridge.abst;

import com.hanyang.bridge.function.RegisterLoginFuncInterface;
import com.hanyang.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractRegisterLoginComponent {
    protected RegisterLoginFuncInterface funcInterface;

    public AbstractRegisterLoginComponent(RegisterLoginFuncInterface funcInterface) {
        validate(funcInterface);
        this.funcInterface = funcInterface;
    }

    protected final void validate(RegisterLoginFuncInterface funcInterface) {
        if (!(funcInterface instanceof RegisterLoginFuncInterface)) {
            throw new UnsupportedOperationException("Unknown register / login function type!");
        }
    }

    public abstract String login(String username, String password);

    public abstract String register(UserInfo userInfo);

    protected abstract boolean checkUserExists(String userName);

    public abstract String login3rd(HttpServletRequest request);
}
