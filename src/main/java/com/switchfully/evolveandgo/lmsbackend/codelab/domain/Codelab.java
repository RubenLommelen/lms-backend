package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;

import javax.persistence.*;

@Entity
@Table(name = "CODELAB")
public class Codelab {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codelab_seq")
    @SequenceGenerator(name = "codelab_seq", sequenceName = "codelab_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    // Create second entity for Colab Progress
    @OneToOne(mappedBy = "")
    private CodelabProgress progress;

    public Codelab() {
    }

    public Codelab(String name, CodelabProgress progress) {
        this.name = name;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public CodelabProgress getProgress() {
        return progress;
    }
}
