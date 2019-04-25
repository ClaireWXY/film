package com.stylefeng.guns.api.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.vo.OrderVO;

/**
 * @author Wang Xueyang
 * @create 2019-04-24
 */
public interface OrderService {
    //验证售出的票的座位是否为真
    boolean isTrueSeats(String fieldId,String seats);

    //已经售出的座位中，有没有这些座位
    boolean isNotSoldSeats(String fieldId,String seats);

    //创建订单信息
    OrderVO saveOrderInfo(Integer fieldId,String soldSeats,String seatName,Integer userId);

    //使用当前登录用户获取已经购买的订单
    Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page);

    //根据场次获取已售座位的编号
    String getSoldSeatsByFieldId(Integer fieldId);

    //根据订单编号获取订单信息
    OrderVO getOrderInfoById(String orderId);

    boolean paySuccess(String orderId);

    boolean payFail(String orderId);
}
