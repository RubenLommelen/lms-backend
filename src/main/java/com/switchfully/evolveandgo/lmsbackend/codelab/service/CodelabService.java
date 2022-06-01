package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.*;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.CodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.progress.dto.ProgressOverviewDto;
import com.switchfully.evolveandgo.lmsbackend.progress.service.ProgressMapper;
import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentCodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CodelabService {

    private final Logger logger = LoggerFactory.getLogger(CodelabService.class);

    private final StudentCodelabProgressMapper studentCodelabProgressMapper;
    private final StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository;
    private final CodelabJpaRepository codelabJpaRepository;
    private final StudentService studentService;
    private final ProgressMapper progressMapper;
    private final StudentJpaRepository studentJpaRepository;

    public CodelabService(StudentCodelabProgressMapper studentCodelabProgressMapper, StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository, CodelabJpaRepository codelabJpaRepository, StudentService studentService, ProgressMapper progressMapper, StudentJpaRepository studentJpaRepository) {
        this.studentCodelabProgressMapper = studentCodelabProgressMapper;
        this.studentCodelabProgressJpaRepository = studentCodelabProgressJpaRepository;
        this.codelabJpaRepository = codelabJpaRepository;
        this.studentService = studentService;
        this.progressMapper = progressMapper;
        this.studentJpaRepository = studentJpaRepository;
    }

    public List<StudentCodelabProgressDto> getCodelabsForStudent(Long id) {
        logger.info("Getting codelabs for student with id: " + id);

        Student student = studentService.findById(id);

        List<StudentCodelabProgress> studentCodelabProgressList = getStudentCodelabProgressList(student);

        List<StudentCodelabProgressDto> studentCodelabProgressDtoList = studentCodelabProgressMapper.toDtoList(studentCodelabProgressList);
        studentCodelabProgressDtoList.sort(Comparator.comparing(studentCodelabProgressDto -> studentCodelabProgressDto.getCodelabCreationDate()));
        return studentCodelabProgressDtoList;
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

    public void saveCodelabProgress(List<CodelabProgressDto> codelabProgressDtoList, Long id) {
        Student student = studentService.findById(id);

        for (CodelabProgressDto codelabProgressDto : codelabProgressDtoList) {

            if (studentCodelabProgressJpaRepository.existsByCodelabIdAndStudentId(codelabProgressDto.getCodelabId(), codelabProgressDto.getStudentId())) {
                StudentCodelabProgress studentProgress = studentCodelabProgressJpaRepository.findByCodelabIdAndStudentId(codelabProgressDto.getCodelabId(), codelabProgressDto.getStudentId());
                studentProgress.setProgress(codelabProgressDto.getProgress());
                studentCodelabProgressJpaRepository.save(studentProgress);
            } else {
                Codelab codelab = codelabJpaRepository.findById(codelabProgressDto.getCodelabId()).get();
                studentCodelabProgressJpaRepository.save(new StudentCodelabProgress(codelabProgressDto.getProgress(), codelab, student));
            }
        }

    }


    public List<ProgressOverviewDto> getProgressOverview() {

        logger.info("fetching progress overview");
        Long amountOfCodelabs = codelabJpaRepository.count();

        List<ProgressOverviewDto> progressOverviewDtoList = progressMapper.toDtoList(studentCodelabProgressJpaRepository.findProgressOverview());

        List<Long> studentIdList = progressOverviewDtoList.stream().map(progress -> progress.getStudentId()).toList();

        List<Student> studentsWithNoProgress = studentJpaRepository.findAll().stream()
                .filter(student -> !studentIdList.contains(student.getId()))
                .toList();

        studentsWithNoProgress.forEach(
                student ->
                        progressOverviewDtoList.add(
                                new ProgressOverviewDto(student.getId(), student.getDisplayName(), 0, amountOfCodelabs)
                        )
        );
        logger.info("fetched progress overview");
        return progressOverviewDtoList;
    }

}

