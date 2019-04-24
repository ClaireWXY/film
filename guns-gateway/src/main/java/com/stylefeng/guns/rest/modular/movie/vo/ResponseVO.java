package com.stylefeng.guns.rest.modular.movie.vo;


import lombok.Data;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */
@Data
public class ResponseVO<M> {

    private int status;//返回状态：0:成功；1:失败；999:系统异常
    private String msg;//具体信息
    private M data;//返回的数据实体
    private String imgPre;//图片前缀
    private int nowPage;//分页
    private int totalPage;

    public ResponseVO() {
    }

    //成功
   /* public static<M> ResponseVO success(int nowPage, int totalPage, String imgPre, M m){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);
        return responseVO;
    }
*/
    /*public static<M> ResponseVO success(String imgPre, M m){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);
        return responseVO;
    }*/

    public static<M> ResponseVO success( M m){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        return responseVO;
    }

  /*  public static<M> ResponseVO success( String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);
        return responseVO;
    }*/

    //失败
    public static <M> ResponseVO fail(String msg){
        ResponseVO responseVO =new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);
        return responseVO;
    }

    //系统异常
    public static<M> ResponseVO sysError(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);
        return responseVO;
    }

}
