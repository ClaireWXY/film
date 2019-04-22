package com.stylefeng.guns.api.movie;

import com.stylefeng.guns.api.movie.vo.BannerVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Wang Xueyang
 * @create 2019-04-22
 */
@Component
public interface MovieService {
    //banners
    List<BannerVO> getBanners();
}
