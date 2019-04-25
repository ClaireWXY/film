package com.stylefeng.guns.api.movie.vo;

import lombok.Data;

/**
 * @author Wang Xueyang
 * @create 2019-04-24
 */
@Data
public class FilmDetailVO {
    private String filmId;
    private String filmName;
    private String filmEnName;
    private String score;
    private String scoreNum;
    private String totalBox;
    private String info01;
    private String info02;
    private String info03;
    private InfoRequestVO info04;
}
