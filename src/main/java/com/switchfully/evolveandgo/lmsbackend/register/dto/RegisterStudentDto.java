package com.switchfully.evolveandgo.lmsbackend.register.dto;

public record RegisterStudentDto(String displayName, String email, String password, String repeatPassword) {
    @Override
    public String toString() {
        return "RegisterStudentDto{" +
                "displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                '}';
    }
}
