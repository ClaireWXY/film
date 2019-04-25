package com.stylefeng.guns.rest.persistence.dao;

import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.rest.persistence.model.MtimeHallFilmInfoT;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 影厅电影信息表 Mapper 接口
 * </p>
 *
 * @author ClaireWang
 * @since 2019-04-21
 */
public interface MtimeHallFilmInfoTMapper extends BaseMapper<MtimeHallFilmInfoT> {

    FilmInfoVO getFilmInfoById(int fieldId);
}
