package com.switchfully.evolveandgo.lmsbackend.login.service;

import com.switchfully.evolveandgo.lmsbackend.login.LoginController;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LoginService {
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);



    public String getToken(String username, String password) throws AuthenticationException {
        try{
            logger.info(username + " attempting to log in.");

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", username);
            map.add("password", password);
            map.add("client_id", "evolveandgo");
            map.add("client_secret", "2dfda5d1-cf05-4c75-b3b4-a7d9d40b73f4");
            map.add("grant_type", "password");

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange("https://keycloak.switchfully.com/auth/realms/java-feb2022/protocol/openid-connect/token",
                            HttpMethod.POST,
                            entity,
                            Map.class);
            String token = (String) response.getBody().get("access_token");
            logger.info(username + " is logged in successfully.");
            return token;
        }catch (Exception e){
            logger.info(username + " failed to log in.");

            throw new AuthenticationException(e.getMessage());
        }
    }



}
