package com.switchfully.evolveandgo.lmsbackend.user.student.dto;

public class StudentDto {
    private final String displayName;
    private final String email;

    public StudentDto(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }
}
