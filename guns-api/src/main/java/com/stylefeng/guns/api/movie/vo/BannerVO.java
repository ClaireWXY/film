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


}
