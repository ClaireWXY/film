package com.stylefeng.guns.rest.modular.order.vo;


import lombok.Data;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */
@Data
public class ResponseVO<T> {

    private int status;//返回状态：0:成功；1:失败；999:系统异常
    private String msg;//具体信息
    private T data;//返回的数据实体
    private String imgPre;//图片前缀
    private int nowPage;//分页
    private int totalPage;

    public ResponseVO() {
    }

    //成功
   public static<T> ResponseVO success(int nowPage, int totalPage, String imgPre, T t){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(t);
        responseVO.setImgPre(imgPre);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);
        return responseVO;
    }

    public static<T> ResponseVO success(String imgPre, T data){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(data);
        responseVO.setImgPre(imgPre);
        return responseVO;
    }

    public static<T> ResponseVO success(T data){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(data);
        return responseVO;
    }

    public static<T> ResponseVO success(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);
        return responseVO;
    }

    //失败
    public static <T> ResponseVO fail(String msg){
        ResponseVO responseVO =new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);
        return responseVO;
    }

    //系统异常
    public static<T> ResponseVO sysError(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);
        return responseVO;
    }

}
