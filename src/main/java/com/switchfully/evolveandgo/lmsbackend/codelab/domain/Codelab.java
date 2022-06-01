package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import javax.persistence.*;
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


    public Codelab() {
    }


    public Codelab(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
