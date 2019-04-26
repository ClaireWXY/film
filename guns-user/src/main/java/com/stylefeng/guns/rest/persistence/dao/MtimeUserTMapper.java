package com.stylefeng.guns.rest.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.user.model.MtimeUserT;
import com.stylefeng.guns.api.user.model.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author ClaireWang
 * @since 2019-04-21
 */
public interface MtimeUserTMapper extends BaseMapper<MtimeUserT>{

    int selectByUsername(@Param("username")String username);


    MtimeUserT findUserByUsernameAndPassWord(@Param("username") String credenceName, @Param("password") String credenceCode);

    boolean updateUser(@Param("user") UserInfo updateUserVo);

    UserInfo selectUserInfoById(@Param("uuid") Integer uuid);
}
