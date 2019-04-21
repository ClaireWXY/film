package com.stylefeng.guns.rest.controller;


import com.stylefeng.guns.rest.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.service.IMtimeUserTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ClaireWang
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/mtimeUserT")
public class MtimeUserTController {

    @Autowired
    IMtimeUserTService userTService;

    @RequestMapping("/select")
     public MtimeUserT findUserById(@RequestParam("UUID") int id){
        MtimeUserT mtimeUserT = userTService.selectById(id);
        return mtimeUserT;
    }
}

