package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgressJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.StudentCodelabProgressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodelabService {

    private final StudentCodelabProgressMapper studentCodelabProgressMapper;
    private final StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository;
    private final StudentJpaRepository studentJpaRepository;

    public CodelabService(StudentCodelabProgressMapper studentCodelabProgressMapper, StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository, StudentJpaRepository studentJpaRepository) {
        this.studentCodelabProgressMapper = studentCodelabProgressMapper;
        this.studentCodelabProgressJpaRepository = studentCodelabProgressJpaRepository;
        this.studentJpaRepository = studentJpaRepository;
    }

    public List<StudentCodelabProgressDto> getCodelabsForStudent(Long id) {
        Student student = studentJpaRepository.findById(id).orElseThrow(() -> {
            throw new StudentNotFoundException(id);
        });
        return studentCodelabProgressJpaRepository.findByStudent(student).stream()
                .map(studentCodelab -> studentCodelabProgressMapper.toDto(studentCodelab))
                .toList();
    }
}
