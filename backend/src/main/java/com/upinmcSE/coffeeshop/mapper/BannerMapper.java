package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.dto.response.BannerResponse;
import com.upinmcSE.coffeeshop.entity.Banner;
import org.springframework.stereotype.Component;

@Component
public class BannerMapper {
    public BannerResponse toBannerResponse(Banner banner){
        return BannerResponse.builder()
                .id(banner.getId())
                .urlImage(banner.getBannerUrl())
                .build();
    }
}
