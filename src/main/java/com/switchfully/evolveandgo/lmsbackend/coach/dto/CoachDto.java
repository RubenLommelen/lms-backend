package com.switchfully.evolveandgo.lmsbackend.coach.dto;

public class CoachDto {
    private final String displayName;
    private final String email;

    public CoachDto(String displayName, String email) {
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
