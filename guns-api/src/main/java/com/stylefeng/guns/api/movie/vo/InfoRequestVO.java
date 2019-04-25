package com.stylefeng.guns.api.movie.vo;

import lombok.Data;

/**
 * @author Wang Xueyang
 * @create 2019-04-24
 */
@Data
public class InfoRequestVO {
    private  String biography;
    private  ActorRequestVO actors;
    private ImgVO imgVO;
    private String filmId;
}
