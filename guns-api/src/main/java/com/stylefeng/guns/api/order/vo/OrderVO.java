package com.stylefeng.guns.api.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wang Xueyang
 * @create 2019-04-24
 */
@Data
public class OrderVO implements Serializable {
    private String orderId;
    private String filmName;
    private String filedTime;
    private String cinemaName;
    private String seatsName;
    private String orderPrice;
    private String orderTimestamp;
    private String orderStatus;
}
