package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCodelabProgressJpaRepository extends JpaRepository<StudentCodelabProgress, Long> {
    List<StudentCodelabProgress> findByStudent(Student student);
}
