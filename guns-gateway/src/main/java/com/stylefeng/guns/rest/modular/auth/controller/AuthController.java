package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.model.MtimeUserT;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Reference(interfaceClass = UserService.class)
    UserService userService;


    @RequestMapping(value = "${jwt.auth-path}")
    public /*ResponseEntity<?>*/Map createAuthenticationToken(AuthRequest authRequest) {
        HashMap map = new HashMap();
        MtimeUserT user = userService.findUserByUsernameAndPassWord(authRequest.getUserName(), authRequest.getCredenceCode());

        if (user!=null) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(JSON.toJSONString(user), randomKey);
            ResponseEntity<AuthResponse> ok = ResponseEntity.ok(new AuthResponse(token, randomKey));
            AuthResponse body = ok.getBody();
            map.put("status",0);
            map.put("data",body);


        } else {
            map.put("status",1);
            map.put("msg",new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR));
        }
        return map;
    }
}
