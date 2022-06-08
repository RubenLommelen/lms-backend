package com.switchfully.evolveandgo.lmsbackend.progress.domain;

import com.switchfully.evolveandgo.lmsbackend.progress.domain.ProgressOverview;
import com.switchfully.evolveandgo.lmsbackend.progress.domain.StudentCodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentCodelabProgressJpaRepository extends JpaRepository<StudentCodelabProgress, Long> {
    List<StudentCodelabProgress> findByStudent(Student student);

    boolean existsByCodelabIdAndStudentId(long codelabId, long studentId);
    StudentCodelabProgress findByCodelabIdAndStudentId(long codelabId, long studentId);
    List<StudentCodelabProgress> findByCodelabId(long codelabId);

    @Query(value = "SELECT fk_student as studentId, count(progress) as numberOfCompletedCodelabs " +
            "FROM student_codelab_progress " +
            "where progress in ('DONE', 'FEEDBACK_NEEDED') " +
            "group by studentId;", nativeQuery = true)
    List<ProgressOverview> findProgressOverview();

}
