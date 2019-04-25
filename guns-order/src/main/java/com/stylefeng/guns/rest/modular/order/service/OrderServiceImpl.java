package com.stylefeng.guns.rest.modular.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.api.cinema.vo.OrderQueryVO;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.rest.modular.order.util.FTPUtil;
import com.stylefeng.guns.rest.persistence.dao.MtimeOrderTMapper;
import com.stylefeng.guns.rest.persistence.model.MtimeOrderT;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.util.resources.cldr.fil.CurrencyNames_fil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Wang Xueyang
 * @create 2019-04-24
 */
@Slf4j
@Component
@Service(interfaceClass = OrderService.class,group = "default")
public class OrderServiceImpl implements OrderService {
    //需要cinemaService中的接口方法获取影片的信息，和影院的信息
    //getFilmInfoByFieldId,getOrderNeed

    @Autowired
    private MtimeOrderTMapper mtimeOrderTMapper;

    @Autowired
    private FTPUtil ftpUtil;

    //需要cinemaService的接口
    @Reference(interfaceClass = CinemaService.class, check = false)
    private CinemaService cinemaService;

    //求订单的总价
    private static double getTotalPrice(int solds,double filmPrice){
        BigDecimal soldDecimal = new BigDecimal(solds);
        BigDecimal filmPriceDecimal = new BigDecimal(filmPrice);
        BigDecimal result = soldDecimal.multiply(filmPriceDecimal);

        //四舍五入，小数点后保留两位
        BigDecimal resultDecimal = result.setScale(2, RoundingMode.HALF_UP);
        return resultDecimal.doubleValue();
    }

    @Override
    public boolean isTrueSeats(String fieldId, String seats) {//是否是真实的座位
        //根据fieldId找到对应的座位的位置图
        String  seatPath = mtimeOrderTMapper.selectSeatsByField(fieldId);
        //找位置图，判断seats是否为真
        String fileStrByAddress = ftpUtil.getFileStrByAddress(seatPath);
        //将位置图信息转换成json对象
        JSONObject jsonObject = JSONObject.parseObject(fileStrByAddress);
        String ids = jsonObject.get("ids").toString();

        //如果找的到，就给isTrue+1
        String[] seatArrs = seats.split(",");
        String[] idArrs = ids.split(",");
        int isTrue =0;
        for (String idArr : idArrs) {
            for (String seatArr : seatArrs) {
                if (seatArr.equalsIgnoreCase(idArr)){
                    isTrue++;
                }
            }
        }
        if (seatArrs.length==isTrue){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {//判断是否是已售出的座位
        EntityWrapper<MtimeOrderT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("field_id",fieldId);

        List<MtimeOrderT> list = mtimeOrderTMapper.selectList(entityWrapper);
        String[] seatArrs = seats.split(",");
        //有任何一个编号可以匹配，则直接返回失败
        for (MtimeOrderT mtimeOrderT : list) {
            String[] ids = mtimeOrderT.getSeatsIds().split(",");
            for (String id : ids) {
                for (String seatArr : seatArrs) {
                    if (id.equalsIgnoreCase(seatArr)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatName, Integer userId) {
        //订单编号
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //影片信息
        FilmInfoVO filmInfoVO = cinemaService.getFilmInfoByFieldId(fieldId);
        Integer filmId = Integer.parseInt(filmInfoVO.getFilmId());
        //获取影院信息
        OrderQueryVO orderQuery = cinemaService.getOrderQuery(fieldId);
        Integer cinemaId  = Integer.parseInt(orderQuery.getCinemaId());
        double filmPrice = Double.parseDouble(orderQuery.getFilmPrice());

        int solds = soldSeats.split(",").length;
        double totalPrice = getTotalPrice(solds, filmPrice);
        MtimeOrderT mtimeOrderT = new MtimeOrderT();
        mtimeOrderT.setUuid(uuid);
        mtimeOrderT.setSeatsName(seatName);
        mtimeOrderT.setSeatsIds(soldSeats);
        mtimeOrderT.setOrderUser(userId);
        mtimeOrderT.setOrderPrice(totalPrice);
        mtimeOrderT.setFilmPrice(filmPrice);
        mtimeOrderT.setFilmId(filmId);
        mtimeOrderT.setFieldId(fieldId);
        mtimeOrderT.setCinemaId(cinemaId);

        Integer insert = mtimeOrderTMapper.insert(mtimeOrderT);
        if (insert>0){
            OrderVO orderVO = mtimeOrderTMapper.selectOrderInfoById(uuid);
            if (orderVO==null||orderVO.getOrderId()==null){
                log.error("订单详情查询失败，订单编号为："+uuid);
                return null;
            }else{
                return orderVO;
            }
        }else {
            log.error("新增订单失败。");
            return null;
        }
    }

    @Override
    public Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page) {
        Page<OrderVO> result = new Page<>();
        if (userId==null){
            log.error("用户信息找不到");
            return null;
        }else{
            List<OrderVO> orders = mtimeOrderTMapper.selectOrderByUserId(userId, page);
            if (orders==null&orders.size()==0){
                result.setTotal(0);
                result.setRecords(new ArrayList<>());
                return result;
            }else {
                //获取订单总数
                EntityWrapper<MtimeOrderT> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("order_user",userId);
                Integer counts = mtimeOrderTMapper.selectCount(entityWrapper);
                result.setTotal(counts);
                result.setRecords(orders);
                return result;
            }
        }
    }

    //获得所有的已售的座位
    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        if(fieldId==null){
            log.error("未找到场次信息。");
            return "";
        }else {
            String soldSeatsByFieldId = mtimeOrderTMapper.selectSoldSeatsByFieldId(fieldId);
            return soldSeatsByFieldId;
        }
    }

    @Override
    public OrderVO getOrderInfoById(String orderId) {
        OrderVO orderVO = mtimeOrderTMapper.selectOrderInfoById(orderId);
        return orderVO;
    }

    @Override
    public boolean paySuccess(String orderId) {
        return false;
    }

    @Override
    public boolean payFail(String orderId) {
        return false;
    }
}
