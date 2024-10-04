package com.upinmcSE.coffeeshop.dto.response.momo;

import lombok.*;
import lombok.experimental.FieldDefaults;
import okhttp3.Headers;
@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MoMoResponse {
    int status;
    String data;
    Headers headers;
}
