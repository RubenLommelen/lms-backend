package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "CODELAB")
public class Codelab {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codelab_seq")
    @SequenceGenerator(name = "codelab_seq", sequenceName = "codelab_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;


    public Codelab() {
    }


    public Codelab(String name, Timestamp creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Codelab codelab = (Codelab) o;
        return Objects.equals(id, codelab.id) && Objects.equals(name, codelab.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
