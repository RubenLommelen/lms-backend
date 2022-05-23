package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.*;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.StudentCodelabProgressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodelabService {

    private final StudentCodelabProgressMapper studentCodelabProgressMapper;
    private final StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final CodelabJpaRepository codelabJpaRepository;

    public CodelabService(StudentCodelabProgressMapper studentCodelabProgressMapper, StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository, StudentJpaRepository studentJpaRepository, CodelabJpaRepository codelabJpaRepository) {
        this.studentCodelabProgressMapper = studentCodelabProgressMapper;
        this.studentCodelabProgressJpaRepository = studentCodelabProgressJpaRepository;
        this.studentJpaRepository = studentJpaRepository;
        this.codelabJpaRepository = codelabJpaRepository;
    }

    public List<StudentCodelabProgressDto> getCodelabsForStudent(Long id) {
        Student student = studentJpaRepository.findById(id).orElseThrow(() -> {
            throw new StudentNotFoundException(id);
        });


        List<StudentCodelabProgress> studentCodeLabProgressForStudent = studentCodelabProgressJpaRepository.findByStudent(student);
        List<Codelab> codelabs = codelabJpaRepository
                .findAll();

        List<Long> codelabProgressIdList = studentCodeLabProgressForStudent.stream()
                .map(studentCodelabProgress -> studentCodelabProgress.getCodelab().getId()).toList();
        List<Codelab> filteredCodelabs = codelabs.stream()
                .filter(codelab -> !codelabProgressIdList.contains(codelab.getId())).toList();

        List<StudentCodelabProgress> codelabsNotStartedList = filteredCodelabs.stream()
                .map(codelab -> new StudentCodelabProgress(CodelabProgress.NOT_STARTED, codelab, student))
                .toList();
        //List<StudentCodelabProgress> codelabsWithoutProgress = codelabsNotStartedList.stream()
        //        .filter(codelab -> !studentCodeLabProgressForStudent.contains(codelab))
        //        .toList();
//
        studentCodeLabProgressForStudent.addAll(codelabsNotStartedList);

        return studentCodelabProgressMapper.toDtoList(studentCodeLabProgressForStudent);


    }
}

