package com.stylefeng.guns.api.movie.vo;

import java.io.Serializable;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */
public class FilmInfo implements Serializable {
    private String filmId;
    private int filmType;
    private String imgAddress;
    private String filmName;
    private String filmScore;
    private int expectNum;
    private  String showTime;
    private int boxNum;
    private String score;

    public FilmInfo() {
    }

    public FilmInfo(String filmId, int filmType, String imgAddress, String filmName, String filmScore, int expectNum, String showTime, int boxNum, String score) {
        this.filmId = filmId;
        this.filmType = filmType;
        this.imgAddress = imgAddress;
        this.filmName = filmName;
        this.filmScore = filmScore;
        this.expectNum = expectNum;
        this.showTime = showTime;
        this.boxNum = boxNum;
        this.score = score;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public int getFilmType() {
        return filmType;
    }

    public void setFilmType(int filmType) {
        this.filmType = filmType;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmScore() {
        return filmScore;
    }

    public void setFilmScore(String filmScore) {
        this.filmScore = filmScore;
    }

    public int getExpectNum() {
        return expectNum;
    }

    public void setExpectNum(int expectNum) {
        this.expectNum = expectNum;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "FilmInfo{" +
                "filmId='" + filmId + '\'' +
                ", filmType=" + filmType +
                ", imgAddress='" + imgAddress + '\'' +
                ", filmName='" + filmName + '\'' +
                ", filmScore='" + filmScore + '\'' +
                ", expectNum=" + expectNum +
                ", showTime='" + showTime + '\'' +
                ", boxNum=" + boxNum +
                ", score='" + score + '\'' +
                '}';
    }
}
