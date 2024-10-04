package com.upinmcSE.coffeeshop.configuration;

import com.upinmcSE.coffeeshop.dto.request.IntrospectRequest;
import com.upinmcSE.coffeeshop.service.impl.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        var response = authenticationService.introspect(IntrospectRequest.builder()
                        .token(token)
                .build());

        if(!response.valid()) throw new JwtException("Token invalid");

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec) // tao 1 doi tuong nimbus jwt de giai ma
                    .macAlgorithm(MacAlgorithm.HS512) // dinh nghia giai ma voi thuat toan nao
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
