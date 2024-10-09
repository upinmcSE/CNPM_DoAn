package com.upinmcSE.coffeeshop.controller;

import com.nimbusds.jose.JOSEException;
import com.upinmcSE.coffeeshop.dto.request.AuthenticationRequest;
import com.upinmcSE.coffeeshop.dto.response.AuthenticationResponse;
import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import com.upinmcSE.coffeeshop.service.impl.AuthenticationServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;


@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationServiceImpl authenticationService;

    @PostMapping("/customer-login")
    public SuccessResponse<AuthenticationResponse> customerLogin(
            @RequestBody AuthenticationRequest request
    )
            throws JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        return SuccessResponse.<AuthenticationResponse>builder()
                .message("Customer login successfully")
                .result(authenticationService.customerLogin(request))
                .build();
    }

}
