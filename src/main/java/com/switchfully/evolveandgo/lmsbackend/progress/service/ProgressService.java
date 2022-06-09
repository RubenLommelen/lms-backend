package com.switchfully.evolveandgo.lmsbackend.progress.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.*;
import com.switchfully.evolveandgo.lmsbackend.progress.dto.*;
import com.switchfully.evolveandgo.lmsbackend.progress.domain.ProgressState;
import com.switchfully.evolveandgo.lmsbackend.progress.domain.StudentCodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.progress.domain.StudentCodelabProgressJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.progress.exception.InvalidProgressException;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.user.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProgressService {

    private final Logger logger = LoggerFactory.getLogger(ProgressService.class);

    private final StudentCodelabProgressMapper studentCodelabProgressMapper;
    private final StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository;
    private final CodelabJpaRepository codelabJpaRepository;
    private final StudentService studentService;
    private final ProgressMapper progressMapper;
    private final StudentJpaRepository studentJpaRepository;

    public ProgressService(StudentCodelabProgressMapper studentCodelabProgressMapper, StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository, CodelabJpaRepository codelabJpaRepository, StudentService studentService, ProgressMapper progressMapper, StudentJpaRepository studentJpaRepository) {
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
                .map(codelab -> new StudentCodelabProgress(ProgressState.NOT_STARTED, codelab, student))
                .toList();

        studentCodelabProgressList.addAll(codelabsNotStartedList);
        return studentCodelabProgressList;
    }

    public void saveCodelabProgress(List<SaveStudentCodelabProgressDto> saveStudentCodelabProgressDtos, Long id) {
        logger.info("Saving codelabs progress for student with id: " + id);

        Student student = studentService.findById(id);

        for (SaveStudentCodelabProgressDto saveStudentCodelabProgressDto : saveStudentCodelabProgressDtos) {

            if (studentCodelabProgressJpaRepository.existsByCodelabIdAndStudentId(saveStudentCodelabProgressDto.getCodelabId(), saveStudentCodelabProgressDto.getStudentId())) {
                StudentCodelabProgress studentProgress = studentCodelabProgressJpaRepository.findByCodelabIdAndStudentId(saveStudentCodelabProgressDto.getCodelabId(), saveStudentCodelabProgressDto.getStudentId());
                studentProgress.setProgress(saveStudentCodelabProgressDto.getProgress());
                studentCodelabProgressJpaRepository.save(studentProgress);
            } else {
                Codelab codelab = codelabJpaRepository.findById(saveStudentCodelabProgressDto.getCodelabId()).get();
                studentCodelabProgressJpaRepository.save(new StudentCodelabProgress(saveStudentCodelabProgressDto.getProgress(), codelab, student));
            }
        }
        logger.info("codelabs progress saved for student with id: " + id);

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

    public CodelabCommentDto saveCodelabComment(CodelabCommentDto codelabCommentDto, Long studentId, Long codelabId) {
        logger.info("Attempting to save comment and/or solution url for student ID " + studentId + " and codelab ID " + codelabId + ".");

        if (!studentCodelabProgressJpaRepository.existsByCodelabIdAndStudentId(codelabId, studentId)) {
            Codelab codelab = codelabJpaRepository.findById(codelabId).get();
            Student student = studentService.findById(studentId);

            StudentCodelabProgress studentCodelabProgress = new StudentCodelabProgress(ProgressState.NOT_STARTED, codelab, student);
            studentCodelabProgress.setComment(codelabCommentDto.getCodelabComment());
            studentCodelabProgressJpaRepository.save(studentCodelabProgress);
            logger.info("Comment and/or solution url saved to progress with student ID " + studentId + " and codelab ID " + codelabId + ".");
            return codelabCommentDto;
        }
        StudentCodelabProgress studentCodelabProgress = studentCodelabProgressJpaRepository.findByCodelabIdAndStudentId(codelabId, studentId);
        studentCodelabProgress.setComment(codelabCommentDto.getCodelabComment());
//        removed constraint due to a bug not allowing a codelab that went from done to incomplete status to save a comment
//        when solved, uncomment the test again
//        if (isSolutionForIncompleteCodelab(codelabCommentDto, studentCodelabProgress)) {
//            logger.error(new InvalidProgressException().getMessage());
//            throw new InvalidProgressException();
//        }
        studentCodelabProgress.setSolutionUrl(codelabCommentDto.getCodelabSolutionUrl());
        studentCodelabProgressJpaRepository.save(studentCodelabProgress);
        logger.info("Comment and/or solution url saved to progress with student ID " + studentId + " and codelab ID " + codelabId + ".");

        return codelabCommentDto;
    }

    private boolean isSolutionForIncompleteCodelab(CodelabCommentDto codelabCommentDto, StudentCodelabProgress studentCodelabProgress) {
        return !studentCodelabProgress.getProgress().codelabCompleted() && !(codelabCommentDto.getCodelabSolutionUrl() == null);
    }

    public List<CodelabSolutionDto> getCodelabSolutions(Long codelabId) {
        List<StudentCodelabProgress> codelabsWithSolutions = studentCodelabProgressJpaRepository.findByCodelabId(codelabId).stream()
                .filter(progress -> isNotNullEmptyBlank(progress))
                .toList();

        return studentCodelabProgressMapper.toSolutionDtoList(codelabsWithSolutions);
    }

    private boolean isNotNullEmptyBlank(StudentCodelabProgress progress) {
        return !(progress.getSolutionUrl() == null || progress.getSolutionUrl().isEmpty() || progress.getSolutionUrl().isBlank());
    }
}