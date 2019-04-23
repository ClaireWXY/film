package com.stylefeng.guns.api.movie.vo;

import java.io.Serializable;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */

public class BannerVO implements Serializable {
    private String bannerId;
    private String bannerAddress;
    private String bannerUrl;

    public BannerVO() {
    }

    public BannerVO(String bannerId, String bannerAddress, String bannerUrl) {
        this.bannerId = bannerId;
        this.bannerAddress = bannerAddress;
        this.bannerUrl = bannerUrl;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerAddress() {
        return bannerAddress;
    }

    public void setBannerAddress(String bannerAddress) {
        this.bannerAddress = bannerAddress;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    @Override
    public String toString() {
        return "BannerVO{" +
                "bannerId='" + bannerId + '\'' +
                ", bannerAddress='" + bannerAddress + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                '}';
    }
}
