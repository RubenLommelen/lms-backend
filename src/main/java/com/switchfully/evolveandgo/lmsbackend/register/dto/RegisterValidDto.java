package com.switchfully.evolveandgo.lmsbackend.register.dto;

public class RegisterValidDto {
    private boolean emailUnique;

    public RegisterValidDto() {
    }

    public RegisterValidDto(boolean emailUnique) {
        this.emailUnique = emailUnique;
    }

    public boolean isEmailUnique() {
        return emailUnique;
    }

    public void setEmailUnique(boolean emailUnique) {
        this.emailUnique = emailUnique;
    }
}
