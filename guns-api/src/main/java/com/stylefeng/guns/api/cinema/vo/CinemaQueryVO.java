package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaQueryVO implements Serializable {

    private Integer brandId=99;
    private Integer areaId=99;
    private Integer halltypeId=99;
    private Integer pageSize=12;
    private Integer nowPage=1;

}
