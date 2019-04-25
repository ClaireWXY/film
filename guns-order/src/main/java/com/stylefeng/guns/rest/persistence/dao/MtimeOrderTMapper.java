package com.stylefeng.guns.rest.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.rest.persistence.model.MtimeOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author ClaireWang
 * @since 2019-04-24
 */
public interface MtimeOrderTMapper extends BaseMapper<MtimeOrderT> {
    String selectSeatsByField(@Param("fieldId") String fieldId);
    OrderVO selectOrderInfoById(@Param("orderId") String orderId);
    List<OrderVO> selectOrderByUserId(@Param("userId") Integer userId, Page<OrderVO> page);
    String selectSoldSeatsByFieldId(@Param("fieldId") Integer fieldId);
}
