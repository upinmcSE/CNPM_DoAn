package com.upinmcSE.coffeeshop.service;

import com.nimbusds.jose.JOSEException;
import com.upinmcSE.coffeeshop.dto.request.*;
import com.upinmcSE.coffeeshop.dto.response.AuthenticationResponse;
import com.upinmcSE.coffeeshop.dto.response.IntrospectResponse;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse customerLogin(AuthenticationRequest request) throws JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;
    AuthenticationResponse employeeLogin(AuthenticationRequest request) throws JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;
    IntrospectResponse introspect(IntrospectRequest request);
    void logout(LogoutRequest request);
    void changePassword(ChangePasswordRequest request);
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

}
