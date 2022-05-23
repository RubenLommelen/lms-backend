package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Codelab;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.StudentCodelabProgressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodelabService {

    private final StudentCodelabProgressMapper studentCodelabProgressMapper;

    public CodelabService(StudentCodelabProgressMapper studentCodelabProgressMapper) {
        this.studentCodelabProgressMapper = studentCodelabProgressMapper;
    }

    public List<StudentCodelabProgressDto> getCodelabsForStudent() {
        return List.of(
                new StudentCodelabProgress(CodelabProgress.DONE, new Codelab("Rinaldo")),
                new StudentCodelabProgress(CodelabProgress.BUSY, new Codelab("Ronaldo"))
        ).stream()
                .map(studentCodelab -> studentCodelabProgressMapper.toDto(studentCodelab))
                .toList();
    }
}
