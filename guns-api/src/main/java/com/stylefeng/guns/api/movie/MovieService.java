package com.stylefeng.guns.api.movie;

import com.stylefeng.guns.api.movie.vo.*;

import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */

public interface MovieService {
    //banners
    List<BannerVO> getBanners();
    //hotFilms
    FilmVO getHotFilms(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    //soonFilms
    FilmVO getSoonFilm(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    //boxRanking
    List<FilmInfo> getBoxRanking();
    //expectRanking
    List<FilmInfo> getExpectRanking();
    //top100
    List<FilmInfo> getTop();

    //按分类条件查询
    List<CatVO> getCats();
    //按片源条件查询
    List<SourceVO> getSources();
    //按年代条件查询
    List<YearVO> getYears();

}
