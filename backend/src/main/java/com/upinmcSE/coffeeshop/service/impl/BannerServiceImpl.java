package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.response.BannerResponse;
import com.upinmcSE.coffeeshop.entity.Banner;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.mapper.BannerMapper;
import com.upinmcSE.coffeeshop.repository.BannerRepository;
import com.upinmcSE.coffeeshop.service.BannerService;
import com.upinmcSE.coffeeshop.utils.FileUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BannerServiceImpl implements BannerService {

    BannerRepository bannerRepository;
    FileUtil fileUtil;
    BannerMapper bannerMapper;
    @Override
    public BannerResponse create(MultipartFile file) throws IOException {
        // Kiểm tra file ảnh có rỗng không
        if (file.isEmpty()) {
            throw new ErrorException(ErrorCode.FILE_EMPTY);
        }

        // Kiểm tra kích thước file và định dạng
        if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
            throw new ErrorException(ErrorCode.FILE_LARGE);
        }

        // Kiểm tra xem có phải file ảnh không
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ErrorException(ErrorCode.INVALID_IMAGE);
        }

        // Lưu file vào hệ thống và lấy tên file
        String filename = fileUtil.storeFile(file);

        Banner banner = Banner.builder()
                .bannerUrl(filename)
                .build();
        banner = bannerRepository.save(banner);

        return BannerResponse.builder()
                .id(banner.getId())
                .urlImage(banner.getBannerUrl())
                .build();
    }

    @Override
    public List<BannerResponse> getBanners() {
        return bannerRepository.findAll()
                .stream()
                .map(bannerMapper::toBannerResponse)
                .toList();
    }


}
