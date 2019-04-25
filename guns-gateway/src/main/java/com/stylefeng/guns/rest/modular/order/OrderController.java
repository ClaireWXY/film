package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.auth.util.TokenBucket;
import com.stylefeng.guns.rest.modular.movie.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-24
 */
@Slf4j
@RestController
@RequestMapping(value = "/order/")
public class OrderController {

    private static TokenBucket tokenBucket =new TokenBucket();

    @Reference(interfaceClass = OrderService.class,check = false)
    OrderService orderService;

    public ResponseVO error(Integer fieldId,String soldSeats,String seatsName){
        return ResponseVO.fail("出现问题了，请稍后重试。");
    }

    //购票
    @RequestMapping(value = "buyTickets",method = RequestMethod.POST)
    public ResponseVO buyTickets(@RequestParam("fieldId") Integer fieldId,
                                 @RequestParam("soldSeats") String soldSeats,
                                 @RequestParam("seatsName") String seatsName) {

        if (tokenBucket.getToken()){
            //售出的票是否是真的
            boolean isTure = orderService.isTrueSeats(fieldId + "", soldSeats);
            //已经售出的座位中，有没有这些座位
            boolean isNotSold = orderService.isNotSoldSeats(fieldId + "", soldSeats);
            //验证，必须保证售出的票是真的，且已经售出的座位中有这个座位，才会创建订单。
            if (isTure&&isNotSold){
                //创建订单信息，获取当前用户信息
                String currentUserId = CurrentUser.getCurrentUser();
                if (currentUserId==null||currentUserId.trim().length()==0){
                    return ResponseVO.fail("用未登录，获取用户信息失败。");
                }
                OrderVO orderVO = orderService.saveOrderInfo(fieldId, soldSeats, seatsName, Integer.parseInt(currentUserId));
                if (orderVO==null){
                    log.error("购票失败！");
                    return ResponseVO.fail("购票业务异常");
                }else {
                    return ResponseVO.success(orderVO);
                }
            }else {
                return ResponseVO.fail("订单中的座位编号有问题，请注意。");
            }
        }else {
            return ResponseVO.fail("请稍后再试。");
        }
    }

    @RequestMapping(value = "getOrderInfo",method = RequestMethod.POST)
    public ResponseVO getOrderInfo(
            @RequestParam(name = "nowPage",required = false,defaultValue = "1")Integer nowPage,
            @RequestParam(name = "pageSize",required = false,defaultValue = "5")Integer pageSize
    ){

        // 获取当前用户的信息
        String userId = CurrentUser.getCurrentUser();

        // 使用当前登陆人获取已经购买的订单
        Page<OrderVO> page = new Page<>(nowPage,pageSize);
        if(userId != null && userId.trim().length()>0){
            Page<OrderVO> result = orderService.getOrderByUserId(Integer.parseInt(userId), page);
            int totalPages = (int)result.getPages();
            List<OrderVO> orderVOList = new ArrayList<>();
            orderVOList.addAll(result.getRecords());

            return ResponseVO.success(nowPage,totalPages,"",orderVOList);

        }else{
            return ResponseVO.fail("用户信息有误。");
        }
    }
}
