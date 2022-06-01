package com.switchfully.evolveandgo.lmsbackend.login.dto;

public class TokenDto {
    private final String token;
    private final String username;
    private final Long id;
    private final UserType userType;


    public TokenDto(String token, String username, Long id, UserType userType) {
        this.token = token;
        this.username = username;
        this.id = id;
        this.userType = userType;
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

    public UserType getUserType() {
        return userType;
    }
}
