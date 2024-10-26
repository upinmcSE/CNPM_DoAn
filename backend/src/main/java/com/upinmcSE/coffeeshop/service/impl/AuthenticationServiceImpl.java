package com.upinmcSE.coffeeshop.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.upinmcSE.coffeeshop.dto.request.*;
import com.upinmcSE.coffeeshop.dto.response.AuthenticationResponse;
import com.upinmcSE.coffeeshop.dto.response.IntrospectResponse;
import com.upinmcSE.coffeeshop.entity.InvalidatedToken;
import com.upinmcSE.coffeeshop.entity.Role;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.repository.CustomerRepository;
import com.upinmcSE.coffeeshop.repository.EmployeeRepository;
import com.upinmcSE.coffeeshop.repository.InvalidatedTokenRepository;
import com.upinmcSE.coffeeshop.service.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    InvalidatedTokenRepository invalidatedTokenRepository;
    CustomerRepository customerRepository;
    EmployeeRepository employeeRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Transactional
    @Override
    public AuthenticationResponse customerLogin(AuthenticationRequest request) throws JOSEException,
            InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var customer = customerRepository.findByUsername(request.username()).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));

        boolean authenticated = passwordEncoder.matches(request.password(), customer.getPassword());

        if(!authenticated) throw new ErrorException(ErrorCode.NOT_MATCH_PW);

        var token = generateToken(customer);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Transactional
    @Override
    public AuthenticationResponse employeeLogin(AuthenticationRequest request) throws JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var employee = employeeRepository.findByUsername(request.username()).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        boolean authenticated = passwordEncoder.matches(request.password(), employee.getPassword());

        if(!authenticated) throw new ErrorException(ErrorCode.NOT_MATCH_PW);

        var token = generateToken(employee);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Transactional
    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.token();
        boolean isValid = true;

        try{
            verifyToken(token);
        }catch (ErrorException | JOSEException | ParseException e){
            isValid =false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Transactional
    @Override
    public void logout(LogoutRequest request) {

    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequest request) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        var customer = customerRepository.findByUsername(username).orElseThrow(() ->
                new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        log.info("password: {}", request.oldPassword());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(!passwordEncoder.matches(request.oldPassword(), customer.getPassword())){
            throw new ErrorException(ErrorCode.NOT_MATCH_PW);
        }
        customer.setPassword(passwordEncoder.encode(request.newPassword()));
        customerRepository.saveAndFlush(customer);
    }

    @Transactional
    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException,
            JOSEException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        var signedJWT = verifyToken(request.token());

        var jit = signedJWT.getJWTClaimsSet().getJWTID(); // lay id cua token
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime(); // lay han cua token

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
        var username = signedJWT.getJWTClaimsSet().getSubject();
        String role = (String) signedJWT.getJWTClaimsSet().getClaim("scope");
        System.out.println(role);
        String[] rolesAndPermissions = role.split(" ");

        Object user = null;

        if(rolesAndPermissions[0].substring(5).equals("CUSTOMER")){
             user = customerRepository.findByUsername(username).orElseThrow(() ->
                    new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        }else {
            user = employeeRepository.findByUsername(username).orElseThrow(() ->
                    new ErrorException(ErrorCode.NOT_FOUND_EMPLOYEE));
        }

         var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }


    private String generateToken(Object obj) throws JOSEException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException
    {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Lấy phương thức getUsername() từ đối tượng
        Method getUsernameMethod = obj.getClass().getMethod("getUsername");
        String username = (String) getUsernameMethod.invoke(obj);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("coffee-shop.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(obj)) // Truyền roles vào hàm buildScope
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jwsObject.serialize();


    }
    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        // check xem token co hop le khong va con han khong
        if (!(verified && expiryTime.after(new Date())))
            throw new ErrorException(ErrorCode.UNAUTHENTICATED);

        // check xem token nay co nam trong cac token da bi huy khong
        if (invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new ErrorException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;

    }

    private String buildScope(Object obj) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        try {
            // Lấy phương thức getRole (trả về một đối tượng Role thay vì danh sách Roles)
            Method getRoleMethod = obj.getClass().getMethod("getRole");
            Role role = (Role) getRoleMethod.invoke(obj); // Lấy role từ đối tượng obj
            if (role != null) {
                // Thêm tên role vào chuỗi
                stringJoiner.add("ROLE_" + role.getName());
                // Nếu role có các permissions, thêm chúng vào chuỗi
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission ->
                            stringJoiner.add(permission.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringJoiner.toString();
    }

}



