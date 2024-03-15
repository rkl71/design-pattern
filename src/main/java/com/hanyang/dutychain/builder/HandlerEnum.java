package com.hanyang.dutychain.builder;

public enum HandlerEnum {
    // 业务投放目的城市
    city("com.hanyang.dutychain.CityHandler"),
    sex("com.hanyang.dutychain.SexHandler"),
    product("com.hanyang.dutychain.ProductHandler");
    String value = "";
    HandlerEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
