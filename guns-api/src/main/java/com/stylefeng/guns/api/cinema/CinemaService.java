package com.stylefeng.guns.api.cinema;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.vo.*;

import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */


public interface CinemaService {

    //1、查询影院列表
    Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);
    //2、查询影院品牌
    List<BrandVO> getBrands(int brandId);
    //3、查询区域列表
    List<AreaVO> getAreas(int areaId);
    //4、查询影厅类型
    List<HallTypeVO> getHallTypes(int hallType);
    //5、根据影院id查询影院信息
    CinemaInfoVO getCinemaInfoById(int cinemaId);
    //6、查询影片信息
    List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId);
    //7、根据放映场次id查询影厅放映信息
    HallInfoVO getHallInfo(int fieldId);
    //8、根据放映场次查询播放的电影编号，然后根据电影编号查询对应的电影信息
    FilmInfoVO getFilmInfoByFieldId(int fieldId);

    //9、订单模块需要接口byClaireWang
    OrderQueryVO getOrderQuery(int fieldId);
}
