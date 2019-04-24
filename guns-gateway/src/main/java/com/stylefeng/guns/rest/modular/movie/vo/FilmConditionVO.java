package com.stylefeng.guns.rest.modular.movie.vo;

import com.stylefeng.guns.api.movie.vo.CatVO;
import com.stylefeng.guns.api.movie.vo.SourceVO;
import com.stylefeng.guns.api.movie.vo.YearVO;
import lombok.Data;

import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */
@Data
public class FilmConditionVO {

    private List<CatVO> catInfo;
    private List<SourceVO> sourceInfo;
    private List<YearVO> yearInfo;
}
