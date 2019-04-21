package com.stylefeng.guns.rest.controller;


import com.stylefeng.guns.rest.persistence.model.MtimeAreaDictT;
import com.stylefeng.guns.rest.service.IMtimeAreaDictTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 地域信息表 前端控制器
 * </p>
 *
 * @author ClaireWang
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/mtimeAreaDictT")
public class MtimeAreaDictTController {

    @Autowired
    IMtimeAreaDictTService areaDictTService;

    @RequestMapping("/select")
    public MtimeAreaDictT findAreaById(@RequestParam("UUID") int UUID) {
        MtimeAreaDictT mtimeAreaDictT = areaDictTService.selectById(UUID);
        return mtimeAreaDictT;
    }
}

