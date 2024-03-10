package com.hanyang.adapter;

import com.alibaba.fastjson.JSONObject;
import com.hanyang.pojo.UserInfo;
import com.hanyang.service.UserService;
import com.hanyang.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Login3rdAdapter extends UserService implements Login3rdTarget {

    @Value("${gitee.state}")
    private String giteeState;
    @Value("${gitee.token.url}")
    private String giteeTokenUrl;
    @Value("${gitee.user.url}")
    private String giteeUserUrl;
    @Value("${gitee.user.prefix}")
    private String giteeUserPrefix;

    @Override
    public String loginByGitee(String code, String state) {
        if (!giteeState.equals(state)) {
            throw new UnsupportedOperationException("Invalid state!");
        }
        String tokenUrl = giteeTokenUrl.concat(code);
        JSONObject tokenResponse = HttpClientUtils.execute(tokenUrl, HttpMethod.POST);
        String token = String.valueOf(tokenResponse.get("access_token"));
        // 请求用户信息，并携带 Token
        String userUrl = giteeUserUrl.concat(token);
        JSONObject userInfoResponse = HttpClientUtils.execute(userUrl, HttpMethod.GET);
        String userName = giteeUserPrefix.concat(String.valueOf(userInfoResponse.get("name")));
        String password = userName;

        return autoRegister3rdAndLogin(userName, password);
    }

    private String autoRegister3rdAndLogin(String userName, String password) {
        if (super.checkUserExists(userName)) {
            return super.login(userName, password);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUserPassword(password);
        userInfo.setCreateDate(new Date());
        super.register(userInfo);
        return super.login(userName, password);
    }

    @Override
    public String loginByWechat(String... params) {
        return null;
    }

    @Override
    public String loginByQQ(String... params) {
        return null;
    }
}
