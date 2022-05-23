package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import javax.persistence.*;

@Entity
@Table(name = "CODELAB_PROGRESS")
public class StudentCodelabProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codelab_progress_seq")
    @SequenceGenerator(name = "codelab_progress_seq", sequenceName = "codelab_progress_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROGRESS")
    private CodelabProgress progress;

    @ManyToOne
    @JoinColumn(name = "FK_CODELAB")
    private Codelab codelab;

    public StudentCodelabProgress(CodelabProgress progress, Codelab codelab) {
        this.progress = progress;
        this.codelab = codelab;
    }


    public StudentCodelabProgress() {
    }

    public Long getId() {
        return id;
    }

    public CodelabProgress getProgress() {
        return progress;
    }

    public Codelab getCodelab() {
        return codelab;
    }
}