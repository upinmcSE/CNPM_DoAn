package com.upinmcSE.coffeeshop.service;

import com.nimbusds.jose.JOSEException;
import com.upinmcSE.coffeeshop.dto.request.AuthenticationRequest;
import com.upinmcSE.coffeeshop.dto.request.IntrospectRequest;
import com.upinmcSE.coffeeshop.dto.request.LogoutRequest;
import com.upinmcSE.coffeeshop.dto.request.RefreshRequest;
import com.upinmcSE.coffeeshop.dto.response.AuthenticationResponse;
import com.upinmcSE.coffeeshop.dto.response.IntrospectResponse;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse customerLogin(AuthenticationRequest request) throws JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;
    AuthenticationResponse employeeLogin(AuthenticationRequest request) throws JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;
    IntrospectResponse introspect(IntrospectRequest request);
    void logout(LogoutRequest request);
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

}
