package com.switchfully.evolveandgo.lmsbackend.user.student.service;

import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterStudentDto;
import com.switchfully.evolveandgo.lmsbackend.register.exception.PasswordsDoNotMatchException;
import com.switchfully.evolveandgo.lmsbackend.user.exception.StudentEmailAlreadyExistsException;
import com.switchfully.evolveandgo.lmsbackend.user.student.dto.StudentDto;
import com.switchfully.evolveandgo.lmsbackend.user.exception.UserNotFoundException;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.StudentJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentJpaRepository studentJpaRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentJpaRepository studentJpaRepository, StudentMapper studentMapper) {
        this.studentJpaRepository = studentJpaRepository;
        this.studentMapper = studentMapper;
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

    public void registerStudent(RegisterStudentDto registerStudentDto) {
        if (!registerStudentDto.getPassword().equals(registerStudentDto.getRepeatPassword())) {
            throw new PasswordsDoNotMatchException();
        }
        if (studentJpaRepository.existsByEmail(registerStudentDto.getEmail()))
        {
            throw new StudentEmailAlreadyExistsException(registerStudentDto.getEmail());
        }
        studentJpaRepository.save(studentMapper.toStudent(registerStudentDto));
    }
}
