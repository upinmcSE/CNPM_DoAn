package com.upinmcSE.coffeeshop.controller;

import com.nimbusds.jose.JOSEException;
import com.upinmcSE.coffeeshop.dto.request.*;
import com.upinmcSE.coffeeshop.dto.response.AuthenticationResponse;
import com.upinmcSE.coffeeshop.dto.response.IntrospectResponse;
import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import com.upinmcSE.coffeeshop.service.impl.AuthenticationServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;


@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
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

    @PostMapping("/employee-login")
    public SuccessResponse<AuthenticationResponse> employeeLogin(
            @RequestBody AuthenticationRequest request
    )
            throws JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        return SuccessResponse.<AuthenticationResponse>builder()
                .message("Customer login successfully")
                .result(authenticationService.employeeLogin(request))
                .build();
    }

    @PostMapping("/introspect")
    SuccessResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return SuccessResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    SuccessResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        var result = authenticationService.refreshToken(request);
        return SuccessResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    SuccessResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return SuccessResponse.<Void>builder().build();
    }

    @PostMapping("/change-password")
    public SuccessResponse<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        authenticationService.changePassword(request);
        return SuccessResponse.<Void>builder()
                .message("Chang password successfully")
                .build();
    }

}
