package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmFieldVO implements Serializable {

    private Integer fieldId;
    private String beginTime;
    private String endTime;
    private String language;
    private String hallName;
    private Integer price;
}
