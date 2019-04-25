package com.stylefeng.guns.rest.modular.movie;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.api.movie.MovieService;
import com.stylefeng.guns.api.movie.vo.*;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.movie.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.movie.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.movie.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

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
       // filmIndexVO.setImgPre(IMGPRE);
        filmIndexVO.setBanners(movieService.getBanners());
        filmIndexVO.setHotFilms(movieService.getHotFilms(true,8,1,1,99,99,99));
        filmIndexVO.setSoonFilms(movieService.getSoonFilm(true,8,1,1,99,99,99));
        filmIndexVO.setBoxRanking(movieService.getBoxRanking());
        filmIndexVO.setExpectRanking(movieService.getExpectRanking());
        filmIndexVO.setTop100(movieService.getTop());
        return ResponseVO.success(IMGPRE,filmIndexVO);
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

   //3.影片查询接口
    @RequestMapping(value = "getfilms",method = RequestMethod.GET)
    public ResponseVO getFilms(@RequestParam(name="showType",required = false,defaultValue = "1") Integer showType,
                               @RequestParam(name = "sortId",required = false,defaultValue = "1") Integer sortId,
                               @RequestParam(name = "catId",required = false,defaultValue = "99") Integer catId,
                               @RequestParam(name ="sourceId",required = false,defaultValue = "99") Integer sourceId,
                               @RequestParam(name ="yearId",required = false,defaultValue = "99") Integer yearId,
                               @RequestParam(name="nowPage",required = false,defaultValue = "1") Integer nowPage,
                               @RequestParam(name = "pageSize",required = false,defaultValue = "18") Integer pageSize){


        FilmVO filmVO=null;
        //根据showType判断影片查询类型
        switch (showType){
            case 1:
                filmVO = movieService.getHotFilms(false, pageSize, nowPage, sortId, sourceId, yearId, catId);
                break;

            case 2:
                filmVO= movieService.getSoonFilm(false, pageSize, nowPage, sortId, sourceId, yearId, catId);
                break;

            case 3:
                break;

            default:
                filmVO = movieService.getHotFilms(false, pageSize, nowPage, sortId, sourceId, yearId, catId);
                break;

        }
        return ResponseVO.success(nowPage,pageSize,IMGPRE,filmVO);
    }

    //4.影片详情查询接口
    @RequestMapping(value = "film/{search}",method = RequestMethod.GET)
    public ResponseVO film(@PathVariable("search") String search,@RequestParam("searchType") Integer searchType){
        FilmDetailVO filmDetail = movieService.getFilmDetail(searchType, search);

        if(filmDetail==null){
            return ResponseVO.fail("没有可查询的影片");
        }else if(filmDetail.getFilmId()==null || filmDetail.getFilmId().trim().length()==0){
            return ResponseVO.fail("没有可查询的影片");
        }

        String filmId = filmDetail.getFilmId();

        // 查询影片的详细信息 -> Dubbo的异步调用
        // 获取影片描述信息

        movieService.getFilmDesc(filmId);
        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();
        // 获取图片信息
        movieService.getImgs(filmId);
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();
        // 导演信息
        movieService.getDectInfo(filmId);
        Future<ActorVO> actorVOFuture = RpcContext.getContext().getFuture();
        // 演员信息
        movieService.getActors(filmId);
        Future<List<ActorVO>> actorsVOFutrue = RpcContext.getContext().getFuture();

        // 包在info对象
        InfoRequestVO infoRequestVO = new InfoRequestVO();

        // 组织Actor属性
        ActorRequestVO actorRequestVO = new ActorRequestVO();

        /*actorRequestVO.setActors(actorsVOFutrue.get());
        actorRequestVO.setActors(actorsVOFutrue.get());
        actorRequestVO.setDirector(actorVOFuture.get());

        // 组织info对象
        infoRequestVO.setActors(actorRequestVO);
        infoRequestVO.setBiography(filmDescVOFuture.get().getBiography());
        infoRequestVO.setFilmId(filmId);
        infoRequestVO.setImgVO(imgVOFuture.get());

        // 组织成返回值
        filmDetail.setInfo04(infoRequstVO);

        return ResponseVO.success("http://img.meetingshop.cn/",filmDetail);
         */
        return null;
    }



}
