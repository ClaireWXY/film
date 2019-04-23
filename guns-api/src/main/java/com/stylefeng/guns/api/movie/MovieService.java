package com.stylefeng.guns.api.movie;

import com.stylefeng.guns.api.movie.vo.BannerVO;
import com.stylefeng.guns.api.movie.vo.FilmInfo;
import com.stylefeng.guns.api.movie.vo.FilmVO;

import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */

public interface MovieService {
    //banners
    List<BannerVO> getBanners();
    //hotFilms
    FilmVO getHotFilms(boolean isLimit,int nums,int nowPage,int sortId,int SourceId,int yearId,int catId);
    //soonFilms
    FilmVO getSoonFilm(boolean isLimit,int nums,int nowPage,int sortId,int SourceId,int yearId,int catId);
    //boxRanking
    List<FilmInfo> getBoxRanking();
    //expectRanking
    List<FilmInfo> getExpectRanking();
    //top100
    List<FilmInfo> getTop();

}
