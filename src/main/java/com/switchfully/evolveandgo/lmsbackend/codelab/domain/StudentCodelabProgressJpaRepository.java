package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCodelabProgressJpaRepository extends JpaRepository<StudentCodelabProgress, Long> {
    List<StudentCodelabProgress> findByStudent(Student student);

    boolean existsByCodelabAndStudent(long codelabId, long studentId);
}
