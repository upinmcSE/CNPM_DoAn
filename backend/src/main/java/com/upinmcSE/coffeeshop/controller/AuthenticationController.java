package com.upinmcSE.coffeeshop.controller;

import com.nimbusds.jose.JOSEException;
import com.upinmcSE.coffeeshop.dto.request.*;
import com.upinmcSE.coffeeshop.dto.response.AuthenticationResponse;
import com.upinmcSE.coffeeshop.dto.response.IntrospectResponse;
import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import com.upinmcSE.coffeeshop.entity.Customer;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.repository.CustomerRepository;
import com.upinmcSE.coffeeshop.service.impl.AuthenticationServiceImpl;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;


@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class AuthenticationController {

    AuthenticationServiceImpl authenticationService;
    CustomerRepository customerRepository;

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

    @PostMapping("/forgot-password")
    public SuccessResponse<Void> forgotPassword(@RequestParam String phoneNumber) throws MessagingException, UnsupportedEncodingException {
        authenticationService.forgotPassword(phoneNumber);
        return SuccessResponse.<Void>builder()
                .message("Send email successfully")
                .build();
    }
    @PostMapping("/check-otp")
    public SuccessResponse<Void> checkOtp(@RequestBody CheckOtpRequest request) {
        System.out.println(request.phone() +"-"+ request.otp());
        Customer customer = customerRepository.findByUsername(request.phone())
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        authenticationService.checkOtp(customer.getEmail(),request);
        return SuccessResponse.<Void>builder()
                .message("Check otp successfully")
                .build();
    }

    @PostMapping("/reset-password")
    public SuccessResponse<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return SuccessResponse.<Void>builder()
                .message("Reset password successfully")
                .build();
    }

}
