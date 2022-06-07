package com.switchfully.evolveandgo.lmsbackend.register.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegisterStudentDto {
    @NotBlank
    private final String displayName;
    @NotBlank
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+){2,4}$", message = "email is not in the right format")
    private final String email;
    @NotBlank() @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@$%^&(){}:;<>,.?/~_+=|])[A-Za-z0-9!@$%^&(){}:;<>,.?/~_+-=|].{7,}$")
    private final String password;
    @NotBlank
    private final String repeatPassword;

    public RegisterStudentDto(String displayName, String email, String password, String repeatPassword) {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

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
