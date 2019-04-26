package com.stylefeng.guns.api.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfo{

    String uuid;
    String username;
    String nickname;
    String email;
    String phone;
    String sex;
    String birthday;
    String lifeState;
    String biography;
    String address;
    String headAddress;
    Date updatetime;
    Date createTime;

}
