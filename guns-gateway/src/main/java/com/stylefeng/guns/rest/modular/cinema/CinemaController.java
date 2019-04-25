package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaConditionResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldsResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */

@Slf4j
@RestController
@RequestMapping(value = "/cinema")
public class CinemaController {

    @Reference(interfaceClass = CinemaService.class)
    CinemaService cinemaService;


    @RequestMapping(value = "getCinemas")
    public ResponseVO getCinemas(CinemaQueryVO cinemaQueryVO) {
        try {
            Page<CinemaVO> cinemas = cinemaService.getCinemas(cinemaQueryVO);
            if (cinemas.getRecords() == null || cinemas.getRecords().size() == 0) {
                return ResponseVO.success("没有影院可查");
            } else {
                return ResponseVO.success(cinemas.getCurrent(), (int) cinemas.getPages(), "", cinemas.getRecords());
            }
        } catch (Exception e) {
            log.error("获取影院列表异常", e);
            return ResponseVO.serviceFail("查询影院列表失败");
        }
    }


    @RequestMapping(value = "getCondition")
    public ResponseVO getCondition(CinemaQueryVO cinemaQueryVO) {

        try {

            List<BrandVO> brands = cinemaService.getBrands(cinemaQueryVO.getBrandId());
            List<AreaVO> areas = cinemaService.getAreas(cinemaQueryVO.getAreaId());
            List<HallTypeVO> hallTypes = cinemaService.getHallTypes(cinemaQueryVO.getHalltypeId());
            CinemaConditionResponseVO cinemaConditionResponseVO = new CinemaConditionResponseVO();
            cinemaConditionResponseVO.setBrandList(brands);
            cinemaConditionResponseVO.setAreaList(areas);
            cinemaConditionResponseVO.setHalltypeList(hallTypes);

            return ResponseVO.success(cinemaConditionResponseVO);
        } catch (Exception e) {
            log.error("获取列表条件失败", e);
            return ResponseVO.serviceFail("获取影院查询条件失败");
        }
    }

    @RequestMapping(value = "getFields")
    public ResponseVO getFields(Integer cinemaId){

        try{

            CinemaInfoVO cinemaInfoVO = cinemaService.getCinemaInfoById(cinemaId);
            List<FilmInfoVO> filmInfoVOS = cinemaService.getFilmInfoByCinemaId(cinemaId);

            CinemaFieldsResponseVO cinemaFieldsResponseVO = new CinemaFieldsResponseVO();
            cinemaFieldsResponseVO.setCinemaInfo(cinemaInfoVO);
            cinemaFieldsResponseVO.setFilmList(filmInfoVOS);
            return ResponseVO.success(cinemaFieldsResponseVO);

        }catch (Exception e){
            log.error("获取播放场次失败", e);
            return ResponseVO.serviceFail("获取失败场次失败");
        }
    }

    @RequestMapping(value = "getFieldInfo")
    public ResponseVO getFieldInfo(Integer cinemaId, Integer fieldId){

        try {
            CinemaInfoVO cinemaInfoVO = cinemaService.getCinemaInfoById(cinemaId);
            FilmInfoVO filmInfoVO = cinemaService.getFilmInfoByFieldId(fieldId);
            HallInfoVO hallInfoVO = cinemaService.getHallInfo(fieldId);

            CinemaFieldResponseVO cinemaFieldResponseVO = new CinemaFieldResponseVO();
            cinemaFieldResponseVO.setCinemaInfo(cinemaInfoVO);
            cinemaFieldResponseVO.setFilmInfo(filmInfoVO);
            cinemaFieldResponseVO.setHallInfo(hallInfoVO);

            return ResponseVO.success(cinemaFieldResponseVO);
        }catch (Exception e){
            log.error("获取选座信息失败", e);
            return ResponseVO.serviceFail("获取选座信息失败");
        }
    }
}

