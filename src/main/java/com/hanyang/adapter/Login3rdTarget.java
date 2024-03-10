package com.hanyang.adapter;

public interface Login3rdTarget {
    public String loginByGitee(String code, String state);

    public String loginByWechat(String... params);

    public String loginByQQ(String... params);
}
