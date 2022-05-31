package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgressJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.progress.dto.ProgressOverviewDto;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentCodelabProgressDto;
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

    public List<ProgressOverviewDto> getProgressOverview() {
        studentCodelabProgressJpaRepository.findProgressOverview().forEach(
                e -> System.out.println("id: " + e.getStudentId() + ", codelabs: " + e.getNumberOfCompletedCodelabs())
        );
        return List.of(
                new ProgressOverviewDto(5L, "Alperen", 3, 12),
                new ProgressOverviewDto(9L, "BakerTheHero", 11, 12),
                new ProgressOverviewDto(1L, "Ruben", 0, 12),
                new ProgressOverviewDto(6L, "Rinaldo", 12, 12)
        );
    }
}

