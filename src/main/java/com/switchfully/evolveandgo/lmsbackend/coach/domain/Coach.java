package com.switchfully.evolveandgo.lmsbackend.coach.domain;

import com.switchfully.evolveandgo.lmsbackend.infrastructure.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "COACH")
public class Coach implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coach_seq")
    @SequenceGenerator(name = "coach_seq", sequenceName = "coach_seq", allocationSize = 1)
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "PASSWORD")
    private String password;




    public Coach() {
    }

    public Coach(String email, String displayName, String password) {
        this.email = email;
        this.displayName = displayName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(email, coach.email) && Objects.equals(displayName, coach.displayName) && Objects.equals(password, coach.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, displayName, password);
    }
}