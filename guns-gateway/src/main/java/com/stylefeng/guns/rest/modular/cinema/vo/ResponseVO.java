package com.stylefeng.guns.rest.modular.cinema.vo;

import lombok.Data;

@Data
public class ResponseVO<T> {

    private int status;
    private String msg;
    private T data;
    private String imgPre;
    private int nowPage;
    private int totalPage;

    private ResponseVO(){}

    public static<T> ResponseVO success(int nowPage, int totalPage, String imgPre, T t){

        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(0);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);
        responseVO.setImgPre(imgPre);
        responseVO.setData(t);

        return responseVO;
    }

    public static<T> ResponseVO success(String imgPre,T t){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(t);
        responseVO.setImgPre(imgPre);

        return responseVO;
    }

    public static<T> ResponseVO success(T t){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(t);

        return responseVO;
    }

    public static<T> ResponseVO success(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<T> ResponseVO serviceFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<T> ResponseVO appFail(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);

        return responseVO;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
