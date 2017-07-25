package com.zity.mshd.http;

/**
 * Created by luochao on 2017/4/14.
 * 接口地址
 */

public class UrlPath {
    public static String BaseUrl="http://211.151.183.170:9081";
    //登录
    public static String LoginUrl=BaseUrl+"/interface/login.do?";
    //获取验证码
    public static String getSecurityCode=BaseUrl+"/interface/sendCode.do?";
    //注册
    public static String SendRegister=BaseUrl+"/interface/register.do?";
    //修改密码
    public static String CHANGE_PASSWORD=BaseUrl+"/interface/savepersonpassword.do?";
    //修改个人信息
    public static String CHANGE_USERINFO=BaseUrl+"/interface/editInfo.do?";
    //获取图片
    public static String BANNER_IMAGE=BaseUrl+"/interface/queryHomeImg.do";
    //获取进度列表
    public static String PROGRESS_LIST=BaseUrl+"/interface/getMyAll.do?";
}
