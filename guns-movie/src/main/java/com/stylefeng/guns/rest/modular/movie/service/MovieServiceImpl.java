package com.stylefeng.guns.rest.modular.movie.service;

import com.alibaba.dubbo.config.annotation.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.movie.MovieService;
import com.stylefeng.guns.api.movie.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.persistence.dao.*;
import com.stylefeng.guns.rest.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import java.util.ArrayList;
import java.util.List;


/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */
@Component
@Service(interfaceClass = MovieService.class)
public class MovieServiceImpl implements MovieService {

    @Autowired
    MtimeActorTMapper mtimeActorTMapper;

    @Autowired
    MtimeBannerTMapper mtimeBannerTMapper;

    @Autowired
    MtimeCatDictTMapper mtimeCatDictTMapper;

    @Autowired
    MtimeFilmActorTMapper mtimeFilmActorTMapper;

    @Autowired
    MtimeFilmInfoTMapper mtimeFilmInfoTMapper;

    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Autowired
    MtimeSourceDictTMapper mtimeSourceDictTMapper;

    @Autowired
    MtimeYearDictTMapper mtimeYearDictTMapper;


    private List<FilmInfo> getFilmInfos(List<MtimeFilmT> films){
        List<FilmInfo> filmInfos = new ArrayList<>();
        for (MtimeFilmT film : films) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(film.getFilmScore());
            filmInfo.setImgAddress(film.getImgAddress());
            filmInfo.setFilmType(film.getFilmType());
            filmInfo.setFilmScore(film.getFilmScore());
            filmInfo.setFilmName(film.getFilmName());
            filmInfo.setFilmId(film.getUuid()+"");
            filmInfo.setExpectNum(film.getFilmPresalenum());
            filmInfo.setBoxNum(film.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(film.getFilmTime()));

            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }

    private MtimeFilmInfoT getFilmInfo(String filmId){
        MtimeFilmInfoT mtimeFilmInfoT = new MtimeFilmInfoT();
        mtimeFilmInfoT.setFilmId(filmId);
        mtimeFilmInfoT = mtimeFilmInfoTMapper.selectOne(mtimeFilmInfoT);
        return mtimeFilmInfoT;
    }

    @Override
    public List<BannerVO> getBanners() {
        ArrayList<BannerVO> result = new ArrayList<>();
        List<MtimeBannerT> banners = mtimeBannerTMapper.selectList(null);

        for (MtimeBannerT banner : banners) {
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerId(banner.getUuid()+"");
            bannerVO.setBannerAddress(banner.getBannerAddress());
            bannerVO.setBannerUrl(banner.getBannerUrl());
            result.add(bannerVO);
        }
        return result;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        //hotFilm限制条件
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");
        //判断是否是getIndex中需要的内容
        if (isLimit){
            //对显示信息的限制条件，限制的列表
            Page<MtimeFilmT> page = new Page<>(1, nums);
            List<MtimeFilmT> mtimeFilms = mtimeFilmTMapper.selectPage(page, entityWrapper);
            //filmInfos
            filmInfos = getFilmInfos(mtimeFilms);
            filmVO.setFilmNum(mtimeFilms.size());
            filmVO.setFilmInfo(filmInfos);
        }else {
            //如果不是在首页中需要信息，是在列表页，内容是热映影片
            Page<MtimeFilmT> page=null;
            //按照sortId的不同，get不同的page对象，1：热门；2:时间；3：评分
            switch (sortId){
                case 1:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage,nums,"film_score");
                    break;
                default:
                    page = new Page<>(nowPage,nums,"film_box_office");
                    break;
            }

            // sourceId,vearId,catId 不为99，则表示要按照对应的编号进行查询
            if (sourceId !=99){
                entityWrapper.eq("film_source",sourceId);
            }
            if (sourceId !=99){
                entityWrapper.eq("film_date",yearId);
            }
            if (catId !=99){
                String catStr="%#"+catId+"%#";
                entityWrapper.like("film_cats",catStr);
            }

            List<MtimeFilmT> mtimeFilms = mtimeFilmTMapper.selectPage(page, entityWrapper);
            //filmInfos
             filmInfos = getFilmInfos(mtimeFilms);
             filmVO.setFilmNum(mtimeFilms.size());

             //总条目数
             int totalCounts=mtimeFilmTMapper.selectCount(entityWrapper);
             int totalPages=(totalCounts / nums)+1;

             filmVO.setFilmInfo(filmInfos);
             filmVO.setTotalPage(totalPages);
             filmVO.setNowPage(nowPage);
        }
        return filmVO;
    }


    @Override
    public FilmVO getSoonFilm(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        //soonFilm的上映条件
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","2");
        //首页是否需要
        if (isLimit){
            Page<MtimeFilmT> page = new Page<>(1, nums);
            List<MtimeFilmT> mtimeFilms = mtimeFilmTMapper.selectPage(page, entityWrapper);
            //filmInfos
            filmInfos=getFilmInfos(mtimeFilms);
            filmVO.setFilmNum(mtimeFilms.size());
            filmVO.setFilmInfo(filmInfos);
        }else{
            //如果不是在首页中需要信息，是在列表页，内容是热映影片
            Page<MtimeFilmT> page=null;
            //按照sortId的不同，get不同的page对象，1：热门；2:时间；3：评分
            switch (sortId){
                case 1:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage,nums,"film_score");
                    break;
                default:
                    page = new Page<>(nowPage,nums,"film_box_office");
                    break;
            }

            // sourceId,vearId,catId 不为99，则表示要按照对应的编号进行查询
            if (sourceId !=99){
                entityWrapper.eq("film_source",sourceId);
            }
            if (yearId !=99){
                entityWrapper.eq("film_date",yearId);
            }
            if (catId !=99){
                String catStr="%#"+catId+"%#";
                entityWrapper.like("film_cats",catStr);
            }


            List<MtimeFilmT> mtimeFilms = mtimeFilmTMapper.selectPage(page, entityWrapper);
            //filmInfos
            filmInfos = getFilmInfos(mtimeFilms);
            filmVO.setFilmNum(mtimeFilms.size());

            //总条目数
            int totalCounts=mtimeFilmTMapper.selectCount(entityWrapper);
            int totalPages=(totalCounts / nums)+1;

            filmVO.setFilmInfo(filmInfos);
            filmVO.setTotalPage(totalPages);
            filmVO.setNowPage(nowPage);

        }
        return null;

    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        //正在上映的，票房前十
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");

        Page<MtimeFilmT> page = new Page<>(1, 10, "film_box_office");
        List<MtimeFilmT> mtimeFilms = mtimeFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> filmInfos = getFilmInfos(mtimeFilms);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        //即将上映，预售前十
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","2");

        Page<MtimeFilmT> page = new Page<>(1,10,"film_preSaleNum");
        List<MtimeFilmT> mtimeFilms = mtimeFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> filmInfos = getFilmInfos(mtimeFilms);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        //正在上映，评分前十
        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");

        Page<MtimeFilmT> page = new Page<>(1, 10, "film_score");
        List<MtimeFilmT> mtimeFilms = mtimeFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> filmInfos = getFilmInfos(mtimeFilms);
        return filmInfos;
    }

    @Override
    public List<CatVO> getCats() {
        ArrayList<CatVO> cats = new ArrayList<>();
        List<MtimeCatDictT> mtimeCats= mtimeCatDictTMapper.selectList(null);//查询影片分类实体对象
        for (MtimeCatDictT mtimeCat : mtimeCats) {
            //将实体对象转换成业务对象
            CatVO catVO = new CatVO();
            catVO.setCatId(mtimeCat.getUuid()+"");
            catVO.setCatName(mtimeCat.getShowName());
            cats.add(catVO);
        }
        return cats;
    }

    @Override
    public List<SourceVO> getSources() {
        ArrayList<SourceVO> sources = new ArrayList<>();
        List<MtimeSourceDictT> mtimeSources = mtimeSourceDictTMapper.selectList(null);
        for (MtimeSourceDictT mtimeSource : mtimeSources) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(mtimeSource.getUuid()+"");
            sourceVO.setSourceName(mtimeSource.getShowName());
            sources.add(sourceVO);
        }
        return sources;
    }

    @Override
    public List<YearVO> getYears() {
        ArrayList<YearVO> years = new ArrayList<>();
        List<MtimeYearDictT> mtimeYears = mtimeYearDictTMapper.selectList(null);
        for (MtimeYearDictT mtimeYear : mtimeYears) {
            YearVO yearVO = new YearVO();
            yearVO.setYearId(mtimeYear.getUuid()+"");
            yearVO.setYearName(mtimeYear.getShowName());
            years.add(yearVO);
        }
        return years;
    }

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String search) {
        FilmDetailVO filmDetailVO=null;
        //searchType 1:名称，2：ID
        if (searchType==1){
            filmDetailVO = mtimeFilmTMapper.selectFilmDetailByName("%" + search + "%");
        }else {
            filmDetailVO = mtimeFilmTMapper.selectFilmDetailById(search);
        }
        return filmDetailVO;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MtimeFilmInfoT filmInfo = getFilmInfo(filmId);
        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(filmInfo.getBiography());
        filmDescVO.setFilmId(filmId);
        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        MtimeFilmInfoT filmInfo = getFilmInfo(filmId);
        String filmImgStr = filmInfo.getFilmImgs();
        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMianImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);

        return imgVO;
    }

    @Override
    public ActorVO getDectInfo(String filmId) {
        MtimeFilmInfoT filmInfo = getFilmInfo(filmId);
        //获取导演信息
        Integer directId=filmInfo.getDirectorId();
        MtimeActorT mtimeActorT = mtimeActorTMapper.selectById(directId);

        ActorVO actorVO = new ActorVO();
        actorVO.setImageAddress(mtimeActorT.getActorImg());
        actorVO.setDirectorName(mtimeActorT.getActorName());
        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        List<ActorVO> actors = mtimeActorTMapper.selectActors(filmId);
        return actors;
    }
}
