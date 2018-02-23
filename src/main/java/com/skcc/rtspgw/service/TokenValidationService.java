package com.skcc.rtspgw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TokenValidationService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${serviceRouter.token.validation.url}")
    private String tokenValidationUrl;

    public boolean validate(String token) {
        Map<String, Boolean> resultMap = restTemplate.getForObject(tokenValidationUrl +"?token={token}", Map.class, token);

        if(resultMap.get("validated")) {
            return true;
        }

        return false;
    }
}
