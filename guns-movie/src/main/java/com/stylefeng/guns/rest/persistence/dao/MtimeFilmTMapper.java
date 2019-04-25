package com.stylefeng.guns.rest.persistence.dao;

import com.stylefeng.guns.api.movie.vo.FilmDetailVO;
import com.stylefeng.guns.rest.persistence.model.MtimeFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author ClaireWang
 * @since 2019-04-21
 */
public interface MtimeFilmTMapper extends BaseMapper<MtimeFilmT> {
    FilmDetailVO selectFilmDetailByName(@Param("filmName") String filmName);
    FilmDetailVO selectFilmDetailById(@Param("uuid") String uuid);

}
