package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.response.BannerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BannerService {
    BannerResponse create(MultipartFile file) throws IOException;
    List<BannerResponse> getBanners();
}
