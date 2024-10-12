package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.response.BannerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BannerService {
    BannerResponse create(MultipartFile file) throws IOException;
}
