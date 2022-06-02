package com.switchfully.evolveandgo.lmsbackend.coach.service;

import com.switchfully.evolveandgo.lmsbackend.coach.domain.Coach;
import com.switchfully.evolveandgo.lmsbackend.coach.dto.CoachDto;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentDto;
import org.springframework.stereotype.Component;

@Component
public class CoachMapper {

    public CoachDto toDto(Coach coach) {
        return new CoachDto(coach.getDisplayName(), coach.getEmail());
    }
}
