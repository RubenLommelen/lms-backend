package com.switchfully.evolveandgo.lmsbackend.progress.domain;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Codelab;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.Student;

import javax.persistence.*;

@Entity
@Table(name = "STUDENT_CODELAB_PROGRESS")
public class StudentCodelabProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codelab_progress_seq")
    @SequenceGenerator(name = "codelab_progress_seq", sequenceName = "codelab_progress_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROGRESS")
    private ProgressState progress;

    @ManyToOne
    @JoinColumn(name = "FK_CODELAB")
    private Codelab codelab;

    @ManyToOne
    @JoinColumn(name = "FK_STUDENT")
    private Student student;

    //add comment table/property

    public StudentCodelabProgress(ProgressState progress, Codelab codelab, Student student) {
        this.progress = progress;
        this.codelab = codelab;
        this.student = student;
    }

    public StudentCodelabProgress() {
    }

    public Long getId() {
        return id;
    }

    public ProgressState getProgress() {
        return progress;
    }

    public Codelab getCodelab() {
        return codelab;
    }

    public Student getStudent() {
        return student;
    }

    public void setProgress(ProgressState progress) {
        this.progress = progress;
    }
}
