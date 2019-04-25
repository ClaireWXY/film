package com.stylefeng.guns.rest.modular.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.persistence.dao.*;
import com.stylefeng.guns.rest.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {


    @Autowired
    MtimeAreaDictTMapper mtimeAreaDictTMapper;

    @Autowired
    MtimeBrandDictTMapper mtimeBrandDictTMapper;

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimeHallDictTMapper mtimeHallDictTMapper;

    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;

    @Autowired
    MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;


    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {

        List<CinemaVO> cinemaVOS = new ArrayList<>();

        Page<MtimeCinemaT> page = new Page<>(cinemaQueryVO.getNowPage(), cinemaQueryVO.getPageSize());

        EntityWrapper<MtimeCinemaT> entityWrapper = new EntityWrapper<>();
        if (cinemaQueryVO.getBrandId() != 99) {
            entityWrapper.eq("brand_id", cinemaQueryVO.getBrandId());
        }
        if (cinemaQueryVO.getAreaId() != 99) {
            entityWrapper.eq("area_id", cinemaQueryVO.getAreaId());
        }
        if (cinemaQueryVO.getHalltypeId() != 99) {
            entityWrapper.like("hall_ids", "%#" + cinemaQueryVO.getHalltypeId() + "#%");
        }

        List<MtimeCinemaT> cinemaTS = mtimeCinemaTMapper.selectPage(page, entityWrapper);
        for (MtimeCinemaT mtimeCinemaT : cinemaTS) {
            CinemaVO cinemaVO = new CinemaVO();

            cinemaVO.setUuid(mtimeCinemaT.getUuid() + "");
            cinemaVO.setCinemaName(mtimeCinemaT.getCinemaName());
            cinemaVO.setAddress(mtimeCinemaT.getCinemaAddress());
            cinemaVO.setMinimumPrice(mtimeCinemaT.getMinimumPrice() + "");

            cinemaVOS.add(cinemaVO);
        }

        Integer counts = mtimeCinemaTMapper.selectCount(entityWrapper);

        Page<CinemaVO> result = new Page<>();
        result.setRecords(cinemaVOS);
        result.setSize(cinemaQueryVO.getPageSize());
        result.setTotal(counts);
        return result;
    }

    @Override
    public List<BrandVO> getBrands(int brandId) {

        boolean flag = false;
        List<BrandVO> brandVOS = new ArrayList<>();
        MtimeBrandDictT brandDictT = mtimeBrandDictTMapper.selectById(brandId);

        if (brandId == 99 || brandDictT == null || brandDictT.getUuid() == null) {
            flag = true;
        }

        List<MtimeBrandDictT> mtimeBrandDictTS = mtimeBrandDictTMapper.selectList(null);
        for (MtimeBrandDictT brand : mtimeBrandDictTS) {
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandName(brand.getShowName());
            brandVO.setBrandId(brand.getUuid() + "");
            if (flag) {
                if (brand.getUuid() == 99) {
                    brandVO.setActive(true);
                } else {
                    if (brand.getUuid() == brandId) {
                        brandVO.setActive(true);
                    }
                }
            }
            brandVOS.add(brandVO);
        }

        return brandVOS;
    }

    @Override
    public List<AreaVO> getAreas(int areaId) {

        boolean flag = false;
        List<AreaVO> areaVOS = new ArrayList<>();
        MtimeAreaDictT mtimeAreaDictT = mtimeAreaDictTMapper.selectById(areaId);

        if (areaId == 99 || mtimeAreaDictT == null || mtimeAreaDictT.getUuid() == null) {
            flag = true;
        }

        List<MtimeAreaDictT> mtimeAreaDictTS = mtimeAreaDictT.selectList(null);
        for (MtimeAreaDictT area : mtimeAreaDictTS) {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaName(area.getShowName());
            areaVO.setAreaId(area.getUuid() + "");
            if (flag) {
                if (area.getUuid() == 99) {
                    areaVO.setActive(true);
                } else {
                    if (area.getUuid() == areaId) {
                        areaVO.setActive(true);
                    }
                }
            }
            areaVOS.add(areaVO);
        }
        return areaVOS;
    }

    @Override
    public List<HallTypeVO> getHallTypes(int hallType) {

        boolean flag = false;
        List<HallTypeVO> hallTypeVOS = new ArrayList<>();
        MtimeHallDictT mtimeHallDictT = mtimeHallDictTMapper.selectById(hallType);

        if (hallType == 99 || mtimeHallDictT == null || mtimeHallDictT.getUuid() == null) {
            flag = true;
        }

        List<MtimeHallDictT> mtimeHallDictTS = mtimeHallDictTMapper.selectList(null);
        for (MtimeHallDictT hall : mtimeHallDictTS) {
            HallTypeVO hallTypeVO = new HallTypeVO();
            hallTypeVO.setHalltypeName(hall.getShowName());
            hallTypeVO.setHalltypeId(hall.getUuid() + "");
            if (flag) {
                if (hall.getUuid() == 99) {
                    hallTypeVO.setActive(true);
                } else {
                    if (hall.getUuid() == hallType) {
                        hallTypeVO.setActive(true);
                    }
                }
            }
            hallTypeVOS.add(hallTypeVO);
        }
        return hallTypeVOS;
    }

    @Override
    public CinemaInfoVO getCinemaInfoById(int cinemaId) {

        MtimeCinemaT cinemaT = mtimeCinemaTMapper.selectById(cinemaId);
        if (cinemaT == null) {
            return new CinemaInfoVO();
        }
        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();
        cinemaInfoVO.setCinemaId(cinemaT.getUuid() + "");
        cinemaInfoVO.setCinemaName(cinemaT.getCinemaName());
        cinemaInfoVO.setCinemaAdress(cinemaT.getCinemaAddress());
        cinemaInfoVO.setCinemaPhone(cinemaT.getCinemaPhone());
        cinemaInfoVO.setImgUrl(cinemaT.getImgAddress());
        return cinemaInfoVO;

    }

    @Override
    public List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId) {

        List<FilmInfoVO> filmInfoVOS = new ArrayList<>();

        List<MtimeHallFilmInfoT> mtimeHallFilmInfoTS = mtimeHallFilmInfoTMapper.selectList(null);
        for (MtimeHallFilmInfoT filmInfo : mtimeHallFilmInfoTS) {
            FilmInfoVO filmInfoVO = new FilmInfoVO();
            filmInfoVO.setActors(filmInfo.getActors());
            filmInfoVO.setFilmCats(filmInfo.getFilmCats());
            filmInfoVO.setFilmId(filmInfo.getFilmId() + "");
            filmInfoVO.setFilmLength(filmInfo.getFilmLength());
            filmInfoVO.setFilmName(filmInfo.getFilmName());
            filmInfoVO.setFilmType("0");
            filmInfoVO.setImgAddress(filmInfo.getImgAddress());

            List<FilmFieldVO> filmFieldVOS = new ArrayList<>();

            EntityWrapper<MtimeFieldT> mtimeFieldTEntityWrapper = new EntityWrapper<>();
            mtimeFieldTEntityWrapper.eq("cinema_id", cinemaId);
            mtimeFieldTEntityWrapper.eq("film_id", filmInfo.getFilmId());
            List<MtimeFieldT> mtimeFieldTS = mtimeFieldTMapper.selectList(mtimeFieldTEntityWrapper);
            for (MtimeFieldT mtimeFieldT : mtimeFieldTS) {
                FilmFieldVO filmFieldVO = new FilmFieldVO();
                filmFieldVO.setFieldId(mtimeFieldT.getUuid());
                filmFieldVO.setBeginTime(mtimeFieldT.getBeginTime());
                filmFieldVO.setEndTime(mtimeFieldT.getEndTime());
                filmFieldVO.setHallName(mtimeFieldT.getHallName());
                filmFieldVO.setPrice(mtimeFieldT.getPrice());
                filmFieldVO.setLanguage(filmInfo.getFilmLanguage());
                filmFieldVOS.add(filmFieldVO);
            }
            filmInfoVO.setFilmFields(filmFieldVOS);
            filmInfoVOS.add(filmInfoVO);
        }
        return filmInfoVOS;
    }

    @Override
    public HallInfoVO getHallInfo(int fieldId) {

        HallInfoVO hallInfoVO = mtimeFieldTMapper.getHallInfo(fieldId);

        return hallInfoVO;
    }



    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId) {

        FilmInfoVO filmInfoVO = mtimeHallFilmInfoTMapper.getFilmInfoById(fieldId);

        return filmInfoVO;
    }
}
