package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.*;
import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentCodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodelabService {

    private final Logger logger = LoggerFactory.getLogger(CodelabService.class);

    private final StudentCodelabProgressMapper studentCodelabProgressMapper;
    private final StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository;
    private final CodelabJpaRepository codelabJpaRepository;
    private final StudentService studentService;

    public CodelabService(StudentCodelabProgressMapper studentCodelabProgressMapper, StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository, CodelabJpaRepository codelabJpaRepository, StudentService studentService) {
        this.studentCodelabProgressMapper = studentCodelabProgressMapper;
        this.studentCodelabProgressJpaRepository = studentCodelabProgressJpaRepository;
        this.codelabJpaRepository = codelabJpaRepository;
        this.studentService = studentService;
    }

    public List<StudentCodelabProgressDto> getCodelabsForStudent(Long id) {
        logger.info("Getting codelabs for student with id: " + id);

        Student student = studentService.findById(id);

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

