package com.zity.mshd.http;

/**
 * Created by luochao on 2017/6/14.
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
    //获取医疗列表
    public static String YILIAO=BaseUrl+"/interface/publicQuery.do?";
    //公共服务详情
    public static String GGFUXQ=BaseUrl+"/interface/getPublicById.do?";
    //提诉求
    public static String LEFT_APPEAL=BaseUrl+"/interface/save.do?";
    //进度详情
    public static String PROGRESS_XQ=BaseUrl+"/interface/getById.do?";
    //提交品论
    public static String MAKE_COMMENT=BaseUrl+"/interface/evaluate.do?";
    //办事指南
    public static String  GUIDE=BaseUrl+"/interface/queryGuide.do";
    //政策法规
    public static String POLICY=BaseUrl+"/interface/queryPolicy.do";
    //通知公告
    public static String NOTICE=BaseUrl+"/interface/queryAnnouncement.do";
}
