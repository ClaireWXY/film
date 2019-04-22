package com.stylefeng.guns.rest.modular.movie;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.movie.MovieService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */

@RestController
@RequestMapping("/film/")
public class MovieController {

    private static final String img_pro="http://img.meetingshop.cn/";

    @Reference(interfaceClass = MovieService.class)
    MovieService movieService;
}
