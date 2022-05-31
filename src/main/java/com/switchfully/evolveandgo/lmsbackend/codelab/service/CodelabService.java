package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.*;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.CodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentCodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CodelabService {

    private final Logger logger = LoggerFactory.getLogger(CodelabService.class);

    private final StudentCodelabProgressMapper studentCodelabProgressMapper;
    private final StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final CodelabJpaRepository codelabJpaRepository;
    private final StudentService studentService;

    public CodelabService(StudentCodelabProgressMapper studentCodelabProgressMapper, StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository, StudentJpaRepository studentJpaRepository, CodelabJpaRepository codelabJpaRepository, StudentService studentService) {
        this.studentCodelabProgressMapper = studentCodelabProgressMapper;
        this.studentCodelabProgressJpaRepository = studentCodelabProgressJpaRepository;
        this.studentJpaRepository = studentJpaRepository;
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

    public boolean saveCodelabProgress(List<CodelabProgressDto> codelabProgressDtoList, Long id) {
        Student student = studentService.findById(id);

        try{
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
            return true;
        }catch (Exception e){
            return false;
        }

    }

}

