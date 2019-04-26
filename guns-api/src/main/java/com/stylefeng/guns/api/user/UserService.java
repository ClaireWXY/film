package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.model.MtimeUserT;
import com.stylefeng.guns.api.user.model.UserInfo;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */
public interface UserService {
    int isUsernameExist(String username);

    boolean insertUser(MtimeUserT user);

    MtimeUserT findUserByUsernameAndPassWord(String credenceName, String credenceCode);

    UserInfo findUserByid(Integer uuid);


    boolean updateUserz(UserInfo userInfo);
}
