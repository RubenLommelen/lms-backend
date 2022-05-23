package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.StudentCodelabProgressDto;
import org.springframework.stereotype.Component;

@Component
public class StudentCodelabProgressMapper {

    public StudentCodelabProgressDto toDto(StudentCodelabProgress studentCodelabProgress) {
        return new StudentCodelabProgressDto(studentCodelabProgress.getProgress(), studentCodelabProgress.getCodelab().getName());
    }
}
