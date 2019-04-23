package com.stylefeng.guns.rest.modular.movie;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.movie.MovieService;
import com.stylefeng.guns.rest.modular.movie.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.movie.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */

@RestController
@RequestMapping("/film/")
public class MovieController {

    @Reference(interfaceClass = MovieService.class)
    MovieService movieService;

    @RequestMapping(value = "getIndex",method = RequestMethod.GET)
    public ResponseVO<FilmIndexVO> getIndex(){
        FilmIndexVO filmIndexVO = new FilmIndexVO();
        filmIndexVO.setBanners(movieService.getBanners());
        filmIndexVO.setHotFilms(movieService.getHotFilms(true,8,1,1,99,99,99));
        filmIndexVO.setSoonFilms(movieService.getSoonFilm(true,8,1,1,99,99,99));
        filmIndexVO.setBoxRanking(movieService.getBoxRanking());
        filmIndexVO.setExpectRanking(movieService.getExpectRanking());
        filmIndexVO.setTop100(movieService.getTop());
         return ResponseVO.success(filmIndexVO);
    }
}
