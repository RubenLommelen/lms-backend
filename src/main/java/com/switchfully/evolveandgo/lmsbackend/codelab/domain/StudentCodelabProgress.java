package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import javax.persistence.*;

@Entity
@Table(name = "CODELAB_PROGRESSION")
public class StudentCodelabProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codelab_progression_seq")
    @SequenceGenerator(name = "codelab_progression_seq", sequenceName = "codelab_progression_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROGRESSION")
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
