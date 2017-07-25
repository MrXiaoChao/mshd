package com.zity.mshd.bean;

/**
 * Created by luochao on 2017/7/19.
 * 用户
 */

public class User {

    /**
     * id : bfe72c72-a2c0-49d6-9813-f324f53fab2e
     * phone : 18701336991
     * address : 1231
     * name : 全局管理员
     * gender : 1
     * success : true
     */

    private String userid;
    private String phone;
    private String address;
    private String name;
    private String gender;
    private boolean success;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
