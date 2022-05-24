package com.switchfully.evolveandgo.lmsbackend.student.dto;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;

import java.util.Objects;

public class StudentCodelabProgressDto {

    private CodelabProgress progress;
    private String codelabName;

    public StudentCodelabProgressDto() {
    }

    public StudentCodelabProgressDto(CodelabProgress progress, String codelabName) {
        this.progress = progress;
        this.codelabName = codelabName;
    }

    public CodelabProgress getProgress() {
        return progress;
    }

    public String getCodelabName() {
        return codelabName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCodelabProgressDto that = (StudentCodelabProgressDto) o;
        return progress == that.progress && Objects.equals(codelabName, that.codelabName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(progress, codelabName);
    }
}
