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

    //通过ID或是名称获取影片信息
    FilmDetailVO getFilmDetail(int searchType,String search);

    //获取影片的描述信息
    FilmDescVO getFilmDesc(String filmId);

    //获取图片信息
    ImgVO getImgs(String filmId);

    //获取导演信息
    ActorVO getDectInfo(String filmId);

    //获取导演信息
    List<ActorVO> getActors(String filmId);


}
