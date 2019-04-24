package com.stylefeng.guns.api.movie.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */
@Data
public class CatVO implements Serializable {
    private String catId;
    private String catName;
    private boolean isActive;
}
