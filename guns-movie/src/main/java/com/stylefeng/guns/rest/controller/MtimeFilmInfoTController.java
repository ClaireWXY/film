package com.stylefeng.guns.rest.controller;


import com.stylefeng.guns.rest.persistence.model.MtimeFilmInfoT;
import com.stylefeng.guns.rest.service.IMtimeFilmInfoTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 影片主表 前端控制器
 * </p>
 *
 * @author ClaireWang
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/mtimeFilmInfoT")
public class MtimeFilmInfoTController {

   @Autowired
    IMtimeFilmInfoTService filmInfoTService;

   @RequestMapping("/select")
   public MtimeFilmInfoT selectFilmInfoById(@RequestParam("UUID") int UUID){
       MtimeFilmInfoT mtimeFilmInfoT = filmInfoTService.selectById(UUID);
       return mtimeFilmInfoT;
   }
}

