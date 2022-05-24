package com.switchfully.evolveandgo.lmsbackend.login.dto;

public class TokenDto {
    private final String token;
    private final String username;
    private final Long id;


    public TokenDto(String token, String username, Long id) {
        this.token = token;
        this.username = username;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }
}
