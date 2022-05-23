package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Codelab;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgressJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.StudentCodelabProgressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodelabService {

    private final StudentCodelabProgressMapper studentCodelabProgressMapper;
    private final StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository;

    public CodelabService(StudentCodelabProgressMapper studentCodelabProgressMapper, StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository) {
        this.studentCodelabProgressMapper = studentCodelabProgressMapper;
        this.studentCodelabProgressJpaRepository = studentCodelabProgressJpaRepository;
    }

    public List<StudentCodelabProgressDto> getCodelabsForStudent() {
        return studentCodelabProgressJpaRepository.findAll().stream()
                .map(studentCodelab -> studentCodelabProgressMapper.toDto(studentCodelab))
                .toList();
    }
}
