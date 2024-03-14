package com.hanyang.utils;

public class Constants {

    // appid
    public static final String APP_ID = "9021000135627608";

    // 应用私钥
    public static final String APP_PRIVATE_KEY = "填上生成的私钥";

    // 支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAovc/2TvwM0HcWrsh41U/VKBNEYybWFSRM7KCXuM/7zj6SSuPd2Ch19RrvCOWEwgl20KY9xzfB9tyuAt8bsP5hAln7JA5ABnDRWm95YCQgu+LiA8MNWz9k6qdiATqoi/AAlBfeuYvm2bvCzTr9VZikoq81VBBDjkk7Tx6qb7852s29z75dqcS3SkeYq25A4CvzUDK85PfHURH9V8Oh90lk4VA/yadOUMZaQmsCmNYFKWK4NuE/G6mVWq1PJuFPPFB/riOJOGOz7EDBI7PQLXTRreGWOumdotNsVRxkyQSpnHklif5ZKPoaVCKXOQT/as54wTnhaQI8/v2jtZCwMfibwIDAQAB";

    // 沙箱接口路径
    public static final String ALIPAY_GATEWAY = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    // 签名方式
    public static final String SIGN_TYPE = "RSA2";

    // 接口回调地址
    public static final String CALLBACK_URL = "http://localhost:8081/order/alipaycallback";
}
