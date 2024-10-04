package com.upinmcSE.coffeeshop.dto.request.momo;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MoMoRequest {
    String method;
    String endpoint;
    String payload;
    String contentType;
}
