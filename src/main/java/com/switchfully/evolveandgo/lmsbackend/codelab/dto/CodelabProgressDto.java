package com.switchfully.evolveandgo.lmsbackend.codelab.dto;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;

public class CodelabProgressDto {
    private final Long studentId;
    private final CodelabProgress CodelabProgress;
    private final Long codelabId;

    public CodelabProgressDto(Long studentId,CodelabProgress codelabProgress, Long codelabId) {
        this.studentId = studentId;
        CodelabProgress = codelabProgress;
        this.codelabId = codelabId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public CodelabProgress getCodelabProgress() {
        return CodelabProgress;
    }

    public Long getCodelabId() {
        return codelabId;
    }

    @Override
    public String toString() {
        return "CodelabProgressDto{" +
                "studentId=" + studentId +
                ", CodelabProgress=" + CodelabProgress +
                ", codelabId=" + codelabId +
                '}';
    }
}
