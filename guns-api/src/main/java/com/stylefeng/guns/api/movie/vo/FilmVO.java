package com.stylefeng.guns.api.movie.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-23
 */
public class FilmVO implements Serializable {
   private int filmNum;
   private int nowPage;
   private int totalPage;
   private List<FilmInfo> filmInfo;

    public FilmVO() {
    }

    public FilmVO(int filmNum, int nowPage, int totalPage, List<FilmInfo> filmInfo) {
        this.filmNum = filmNum;
        this.nowPage = nowPage;
        this.totalPage = totalPage;
        this.filmInfo = filmInfo;
    }

    public int getFilmNum() {
        return filmNum;
    }

    public void setFilmNum(int filmNum) {
        this.filmNum = filmNum;
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

    public List<FilmInfo> getFilmInfo() {
        return filmInfo;
    }

    public void setFilmInfo(List<FilmInfo> filmInfo) {
        this.filmInfo = filmInfo;
    }

    @Override
    public String toString() {
        return "FilmVO{" +
                "filmNum=" + filmNum +
                ", nowPage=" + nowPage +
                ", totalPage=" + totalPage +
                ", filmInfo=" + filmInfo +
                '}';
    }
}
