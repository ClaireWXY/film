package com.stylefeng.guns.rest.modular.movie;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.movie.MovieService;
import com.stylefeng.guns.api.movie.vo.CatVO;
import com.stylefeng.guns.api.movie.vo.FilmVO;
import com.stylefeng.guns.api.movie.vo.SourceVO;
import com.stylefeng.guns.api.movie.vo.YearVO;
import com.stylefeng.guns.rest.modular.movie.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.movie.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.movie.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */

@RestController
@RequestMapping("/film/")
public class MovieController {

    private static final String IMGPRE="http://img.meetingshop.cn/";

    @Reference(interfaceClass = MovieService.class)
    MovieService movieService;

    //1、首页接口
    @RequestMapping(value = "getIndex",method = RequestMethod.GET)
    public ResponseVO<FilmIndexVO> getIndex(){
        FilmIndexVO filmIndexVO = new FilmIndexVO();
        filmIndexVO.setImgPre(IMGPRE);
        filmIndexVO.setBanners(movieService.getBanners());
        filmIndexVO.setHotFilms(movieService.getHotFilms(true,8,1,1,99,99,99));
        filmIndexVO.setSoonFilms(movieService.getSoonFilm(true,8,1,1,99,99,99));
        filmIndexVO.setBoxRanking(movieService.getBoxRanking());
        filmIndexVO.setExpectRanking(movieService.getExpectRanking());
        filmIndexVO.setTop100(movieService.getTop());
        return ResponseVO.success(filmIndexVO);
    }

    //2、影片条件列表查询接口
   @RequestMapping(value="getConditionList",method = RequestMethod.GET)
    public ResponseVO getConditionList(@RequestParam(name="catId",defaultValue = "99",required = false) String catId,
                                       @RequestParam(name="sourceId",defaultValue = "99",required = false) String sourceId,
                                       @RequestParam(name="yearId",defaultValue = "99",required = false) String yearId){
       FilmConditionVO filmConditionVO = new FilmConditionVO();

       //cats
       boolean flag =false;
       List<CatVO> cats = movieService.getCats();
       List<CatVO> result1 = new ArrayList<>();

       CatVO cat=null;
       for (CatVO catVO : cats) {
           //判断是否存在catId，存在，则改变状态
           if (catVO.getCatId().equals("99")){
               cat=catVO;
               continue;
           }
           if (catVO.getCatId().equals(catId)){
                flag=true;
                catVO.setActive(flag);
           }else {
               catVO.setActive(false);
           }
           result1.add(catVO);
       }
       //没有catId，默认变成Active状态
       if (!flag){
           cat.setActive(true);
           result1.add(cat);
       }else {
           cat.setActive(false);
           result1.add(cat);
       }

       //sources
       flag=false;
       List<SourceVO> sources = movieService.getSources();
       ArrayList<SourceVO> result2 = new ArrayList<>();
       SourceVO sourceVO =null;
       for (SourceVO source : sources) {
           if (source.getSourceId().equals("99")){
               sourceVO=source;
               continue;
           }
           if (source.getSourceId().equals(sourceId)){
               flag=true;
               source.setActive(true);
           }else {
               source.setActive(false);
           }
           result2.add(source);
       }
       if (!flag){
           sourceVO.setActive(true);
           result2.add(sourceVO);
       }else{
           sourceVO.setActive(false);
           result2.add(sourceVO);
       }

       //years
        flag=false;
       List<YearVO> years = movieService.getYears();
       ArrayList<YearVO> result3 = new ArrayList<>();
       YearVO yearVO =null;
       for (YearVO year : years) {
           if (year.getYearId().equals("99")){
               yearVO=year;
               continue;
           }
           if (year.getYearId().equals(yearId)){
               flag=true;
               year.setActive(true);
           }else {
               year.setActive(false);
           }
           result3.add(year);
       }
       if (!flag){
           yearVO.setActive(true);
           result3.add(yearVO);
       }else{
           yearVO.setActive(false);
           result3.add(yearVO);
       }

       filmConditionVO.setCatInfo(result1);
       filmConditionVO.setSourceInfo(result2);
       filmConditionVO.setYearInfo(result3);

       return ResponseVO.success(filmConditionVO);
   }



}
