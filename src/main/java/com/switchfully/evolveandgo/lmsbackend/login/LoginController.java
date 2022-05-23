package com.switchfully.evolveandgo.lmsbackend.login;

import com.switchfully.evolveandgo.lmsbackend.login.dto.LoginDto;
import com.switchfully.evolveandgo.lmsbackend.login.dto.TokenDto;
import com.switchfully.evolveandgo.lmsbackend.login.service.LoginService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("login")
public class LoginController {


    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @CrossOrigin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto login(@RequestBody LoginDto loginDto) throws AuthenticationException {
        return new TokenDto(loginService.getToken(loginDto.getUsername(), loginDto.getPassword()));
    }

}