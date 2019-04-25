package com.stylefeng.guns.api.movie.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-24
 */
@Data
public class ActorRequestVO {
    private ActorVO derector;
    private List<ActorVO> actors;
}
