package com.switchfully.evolveandgo.lmsbackend.user.coach.service;

import com.switchfully.evolveandgo.lmsbackend.user.coach.domain.Coach;
import com.switchfully.evolveandgo.lmsbackend.user.coach.dto.CoachDto;
import org.springframework.stereotype.Component;

@Component
public class CoachMapper {

    public CoachDto toDto(Coach coach) {
        return new CoachDto(coach.getDisplayName(), coach.getEmail());
    }
}
