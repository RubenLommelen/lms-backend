package com.switchfully.evolveandgo.lmsbackend.user.student.service;

import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterStudentDto;
import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterValidDto;
import com.switchfully.evolveandgo.lmsbackend.register.exception.PasswordsDoNotMatchException;
import com.switchfully.evolveandgo.lmsbackend.security.KeycloakService;
import com.switchfully.evolveandgo.lmsbackend.security.KeycloakUserDto;
import com.switchfully.evolveandgo.lmsbackend.security.Role;
import com.switchfully.evolveandgo.lmsbackend.user.exception.StudentEmailAlreadyExistsException;
import com.switchfully.evolveandgo.lmsbackend.user.student.dto.StudentDto;
import com.switchfully.evolveandgo.lmsbackend.user.exception.UserNotFoundException;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.StudentJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentJpaRepository studentJpaRepository;
    private final StudentMapper studentMapper;
    private final KeycloakService keycloakService;

    public StudentService(StudentJpaRepository studentJpaRepository, StudentMapper studentMapper, KeycloakService keycloakService) {
        this.studentJpaRepository = studentJpaRepository;
        this.studentMapper = studentMapper;
        this.keycloakService = keycloakService;
    }

    public Student findById(Long id){
        return studentJpaRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(id);
        });
    }

    public Student findByEmail(String email){
        return studentJpaRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException(email);
        });
    }

    public StudentDto getStudentDtoById(Long id) {
        return studentMapper.toDto(findById(id));
    }

    public RegisterValidDto registerStudent(RegisterStudentDto registerStudentDto) {
        logger.info("Started registering: " + registerStudentDto.getEmail());

        RegisterValidDto registerValidDto = new RegisterValidDto(true);

        if (!registerStudentDto.getPassword().equals(registerStudentDto.getRepeatPassword())) {
            logger.error("Passwords do not match for: " + registerStudentDto.getEmail());
            throw new PasswordsDoNotMatchException();
        }
        if (studentJpaRepository.existsByEmail(registerStudentDto.getEmail())) {
            logger.error("Student with email: " + registerStudentDto.getEmail() + " already exists");
            registerValidDto.setEmailUnique(false);
//            throw new StudentEmailAlreadyExistsException(registerStudentDto.getEmail());
        }

        if (registerValidDto.isEmailUnique()) {
            studentJpaRepository.save(studentMapper.toStudent(registerStudentDto));
            keycloakService.addUser(new KeycloakUserDto(registerStudentDto.getEmail(), registerStudentDto.getPassword(), Role.STUDENT));
            logger.info("Finished registering: " + registerStudentDto.getEmail());
        }

        return registerValidDto;
    }
}
