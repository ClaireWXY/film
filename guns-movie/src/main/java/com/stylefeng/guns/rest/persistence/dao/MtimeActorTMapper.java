package com.stylefeng.guns.rest.persistence.dao;

import com.stylefeng.guns.api.movie.vo.ActorVO;
import com.stylefeng.guns.rest.persistence.model.MtimeActorT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author ClaireWang
 * @since 2019-04-21
 */
public interface MtimeActorTMapper extends BaseMapper<MtimeActorT> {
    List<ActorVO> selectActors(@Param("filmId") String filmId);


}
