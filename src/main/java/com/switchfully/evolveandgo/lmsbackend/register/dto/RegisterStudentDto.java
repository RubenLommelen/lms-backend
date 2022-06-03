package com.switchfully.evolveandgo.lmsbackend.register.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record RegisterStudentDto(
        @NotBlank
        String displayName,
        @NotBlank @Email(regexp = "/^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/", message = "email is not in the right format")
        String email,
        @NotBlank() @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@$%^&(){}:;<>,.?/~_+=|])[A-Za-z0-9!@$%^&(){}:;<>,.?/~_+-=|].{7,}$")
        String password,
        @NotBlank
        String repeatPassword) {
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
