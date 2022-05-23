package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.*;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.StudentCodelabProgressDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodelabService {

    private final StudentCodelabProgressMapper studentCodelabProgressMapper;
    private final StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final CodelabJpaRepository codelabJpaRepository;
    private final Logger logger = LoggerFactory.getLogger(CodelabService.class);

    public CodelabService(StudentCodelabProgressMapper studentCodelabProgressMapper, StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository, StudentJpaRepository studentJpaRepository, CodelabJpaRepository codelabJpaRepository) {
        this.studentCodelabProgressMapper = studentCodelabProgressMapper;
        this.studentCodelabProgressJpaRepository = studentCodelabProgressJpaRepository;
        this.studentJpaRepository = studentJpaRepository;
        this.codelabJpaRepository = codelabJpaRepository;
    }

    public List<StudentCodelabProgressDto> getCodelabsForStudent(Long id) {
        logger.info("Getting codelabs for student with id: " + id);
        Student student = studentJpaRepository.findById(id).orElseThrow(() -> {
            logger.error(new StudentNotFoundException(id).getMessage());
            throw new StudentNotFoundException(id);
        });

        List<StudentCodelabProgress> studentCodelabProgressList = getStudentCodelabProgressList(student);

        return studentCodelabProgressMapper.toDtoList(studentCodelabProgressList);


    }

    private List<StudentCodelabProgress> getStudentCodelabProgressList(Student student) {
        List<StudentCodelabProgress> studentCodelabProgressList = studentCodelabProgressJpaRepository.findByStudent(student);
        List<Long> codelabProgressIdList = studentCodelabProgressList.stream()
                .map(studentCodelabProgress -> studentCodelabProgress.getCodelab().getId())
                .toList();

        List<StudentCodelabProgress> codelabsNotStartedList = codelabJpaRepository.findAll().stream()
                .filter(codelab -> !codelabProgressIdList.contains(codelab.getId()))
                .map(codelab -> new StudentCodelabProgress(CodelabProgress.NOT_STARTED, codelab, student))
                .toList();

        studentCodelabProgressList.addAll(codelabsNotStartedList);
        return studentCodelabProgressList;
    }
}

