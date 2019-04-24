package com.stylefeng.guns.api.movie.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */
@Data
public class SourceVO implements Serializable {
    private String sourceId;
    private String sourceName;
    private boolean isActive;
}
