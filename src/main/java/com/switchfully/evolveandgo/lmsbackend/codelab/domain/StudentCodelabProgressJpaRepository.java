package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import com.switchfully.evolveandgo.lmsbackend.progress.domain.ProgressOverview;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentCodelabProgressJpaRepository extends JpaRepository<StudentCodelabProgress, Long> {
    List<StudentCodelabProgress> findByStudent(Student student);

//    @Query(value = "SELECT fk_student as studentId, count(progress) as numberOfCompletedCodelabs " +
//            "FROM codelab_progress " +
//            "where progress in ('DONE', 'FEEDBACK_NEEDED') " +
//            "group by studentId;", nativeQuery = true)
    @Query(value = "SELECT new com.switchfully.evolveandgo.lmsbackend.progress.domain.ProgressOverview(c.student.id, count(c)) as numberOfCompletedCodelabs " +
            "FROM StudentCodelabProgress c " +
            "where c.progress in (com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress.DONE, com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress.FEEDBACK_NEEDED) " +
            "group by c.student.id")
    List<ProgressOverview> findProgressOverview();

    // @Query("select c from Country c where c.name = :name")
    //Country findByName(@Param("name") String name);

}
