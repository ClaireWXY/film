package com.stylefeng.guns.rest.modular.movie.vo;

import com.stylefeng.guns.api.movie.vo.BannerVO;
import com.stylefeng.guns.api.movie.vo.FilmInfo;
import com.stylefeng.guns.api.movie.vo.FilmVO;
import lombok.Data;

import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */
@Data
public class FilmIndexVO {
    private String imgPre;
    private List<BannerVO> banners;
    private FilmVO hotFilms;
    private FilmVO soonFilms;
    private List<FilmInfo> boxRanking;
    private List<FilmInfo> expectRanking;
    private List<FilmInfo> top100;
}
