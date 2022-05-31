package com.switchfully.evolveandgo.lmsbackend.student.dto;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;

import java.util.Objects;

public class StudentCodelabProgressDto {
    private Long codelabId;
    private CodelabProgress progress;
    private String codelabName;

    public StudentCodelabProgressDto(Long codelabId,CodelabProgress progress, String codelabName) {
        this.codelabId = codelabId;

        this.progress = progress;
        this.codelabName = codelabName;
    }

    public Long getCodelabId() {
        return codelabId;
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
        return Objects.equals(codelabId, that.codelabId) && progress == that.progress && Objects.equals(codelabName, that.codelabName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codelabId, progress, codelabName);
    }

    @Override
    public String toString() {
        return "StudentCodelabProgressDto{" +
                "codelabId=" + codelabId +
                ", progress=" + progress +
                ", codelabName='" + codelabName + '\'' +
                '}';
    }
}
