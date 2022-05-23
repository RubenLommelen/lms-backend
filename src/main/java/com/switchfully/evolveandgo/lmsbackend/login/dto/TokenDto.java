package com.switchfully.evolveandgo.lmsbackend.login.dto;

public class TokenDto {
    private final String token;
    private final String username;


    public TokenDto(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
