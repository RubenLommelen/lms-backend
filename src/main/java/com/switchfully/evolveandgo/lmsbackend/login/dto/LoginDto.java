package com.switchfully.evolveandgo.lmsbackend.login.dto;

import javax.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank(message = "Email was invalid")
    private final String email;
    @NotBlank(message = "Password was invalid")
    private final String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

