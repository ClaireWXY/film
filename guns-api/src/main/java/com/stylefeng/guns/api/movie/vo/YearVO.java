package com.stylefeng.guns.api.movie.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */
@Data
public class YearVO implements Serializable {
    private String yearId;
    private String yearName;
    private boolean isActive;
}
