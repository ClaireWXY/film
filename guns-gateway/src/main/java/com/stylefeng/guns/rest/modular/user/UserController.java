package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.model.MtimeUserT;
import com.stylefeng.guns.api.user.model.UserInfo;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference(interfaceClass = UserService.class)
    UserService userService;

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Map userIsExist(String username) {
        HashMap map = new HashMap();
        int i = userService.isUsernameExist(username);
        if (i == 0 && username != null && !"".equals(username)) {

            map.put("msg", "“验证成功”");
            map.put("status", 0);
        } else {

            map.put("msg", "用户已存在");
            map.put("status", 1);
        }

        return map;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map register(String username, String password, String email, String phone, String address) {
        HashMap map = new HashMap();
        int i = userService.isUsernameExist(username);
        if (username != null && !"".equals(username) && password != null && !"".equals(password) && i == 0) {
            MtimeUserT user = new MtimeUserT();
            user.setUserName(username);
            user.setUserPwd(password);
            if (email != null && !"".equals(email)) {
                user.setEmail(email);
            }
            if (phone != null && !"".equals(phone)) {
                user.setUserPhone(phone);
            }
            if (address != null && !"".equals(address)) {
                user.setAddress(address);
            }

            boolean insert = userService.insertUser(user);

            if (insert) {
                map.put("status", 0);
                map.put("msg", "“注册成功”");
            } else {
                map.put("status", 1);
                map.put("msg", "“用户已存在”");
            }

        } else {
            map.put("status", 1);
            map.put("msg", "“用户已存在”");
        }
        return map;

    }

    @RequestMapping("/logout")
    public Map logout(HttpServletRequest request, HttpServletResponse response) {
        HashMap map = new HashMap();
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        }
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(authToken);
        MtimeUserT mtimeUserT = JSONObject.parseObject(usernameFromToken, MtimeUserT.class);
        if(userService.isUsernameExist(mtimeUserT.getUserName())!=0){
            map.put("status", 0);
            map.put("msg", "成功退出");
        }else {
            map.put("status", 1);
            map.put("msg", "退出失败，用户尚未登陆");
        }
        return map;
    }


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;



    @RequestMapping("/getUserInfo")
    public Map getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        HashMap map = new HashMap();
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        }
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(authToken);
        MtimeUserT mtimeUserT = JSONObject.parseObject(usernameFromToken, MtimeUserT.class);
        UserInfo userInfo=userService.findUserByid(mtimeUserT.getUuid());
        System.out.println(userInfo); System.out.println(userInfo);

        if(userInfo!=null){
            map.put("status",0);
            map.put("data",userInfo);
        }else {
            map.put("status",1);
            map.put("msg","查询失败用户未登录");
        }
        return map;


    }


    @RequestMapping(value = "/updateUserInfo")
    public Map updateUserInfo(UserInfo userInfo){
        HashMap map = new HashMap();
        boolean b = userService.updateUserz(userInfo);
        return map;
    }


}
