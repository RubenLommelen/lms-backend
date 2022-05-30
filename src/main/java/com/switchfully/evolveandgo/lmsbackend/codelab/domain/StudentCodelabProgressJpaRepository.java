package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import com.switchfully.evolveandgo.lmsbackend.progress.domain.ProgressOverview;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentCodelabProgressJpaRepository extends JpaRepository<StudentCodelabProgress, Long> {
    List<StudentCodelabProgress> findByStudent(Student student);

    @Query(value = "SELECT fk_student as studentId, count(progress) as numberOfCompletedCodelabs " +
            "FROM codelab_progress " +
            "where progress in ('DONE', 'FEEDBACK_NEEDED') " +
            "group by studentId;", nativeQuery = true)
    List<ProgressOverview> findProgressOverview();

}
