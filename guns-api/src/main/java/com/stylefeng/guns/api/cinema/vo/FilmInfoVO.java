package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmInfoVO implements Serializable {

    private String filmId;
    private String filmName;
    private String filmType;
    private String imgAddress;
    private String filmCats;
    private String filmLength;
    private String actors;
    private List<FilmFieldVO> filmFields;
}
