package com.stylefeng.guns.rest.persistence.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserService;

import com.stylefeng.guns.api.user.model.MtimeUserT;
import com.stylefeng.guns.api.user.model.UserInfo;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.persistence.dao.MtimeUserTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Service(interfaceClass = UserService.class)
public class UserTServiceImpl implements UserService {
    @Autowired
    MtimeUserTMapper mtimeUserTMapper;
    @Override
    public int isUsernameExist(String username) {
        int i =mtimeUserTMapper.selectByUsername(username);
        return i;
    }

    @Override
    public boolean insertUser(MtimeUserT user) {
        String userPwd = user.getUserPwd();
        String encrypt = MD5Util.encrypt(userPwd);
        user.setUserPwd(encrypt);
        Integer insert = mtimeUserTMapper.insert(user);

        return insert!=0;
    }

    @Override
    public MtimeUserT findUserByUsernameAndPassWord(String credenceName, String credenceCode) {
        String encrypt = MD5Util.encrypt(credenceCode);
        credenceCode=encrypt;
        MtimeUserT userByUsernameAndPassWord = mtimeUserTMapper.findUserByUsernameAndPassWord(credenceName, credenceCode);
        return userByUsernameAndPassWord;
    }

    @Override
    public UserInfo findUserByid(Integer uuid) {
        UserInfo userInfo = mtimeUserTMapper.selectUserInfoById(uuid);
        return userInfo;
    }

    @Override
    public boolean updateUserz(UserInfo userInfo) {
        return false;
    }


}
