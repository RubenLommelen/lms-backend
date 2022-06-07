package com.switchfully.evolveandgo.lmsbackend.progress.dto;

import com.switchfully.evolveandgo.lmsbackend.progress.domain.ProgressState;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class StudentCodelabProgressDto {
    private final Long codelabId;
    private final ProgressState progress;
    private final String codelabName;
    private final Date codelabCreationDate;
    private final String codelabComment;
    private final String codelabSolutionUrl;
    private final boolean codelabCompleted;

    public StudentCodelabProgressDto(Long codelabId, ProgressState progress, String codelabName, Timestamp codelabCreationDate, String codelabComment, String codelabSolutionUrl) {
        this.codelabId = codelabId;

        this.progress = progress;
        this.codelabName = codelabName;
        this.codelabCreationDate = codelabCreationDate;
        this.codelabComment = codelabComment;
        this.codelabSolutionUrl = codelabSolutionUrl;
        this.codelabCompleted = progress.codelabCompleted();
    }

    public Long getCodelabId() {
        return codelabId;
    }


    public ProgressState getProgress() {
        return progress;
    }

    public String getCodelabName() {
        return codelabName;
    }

    public Date getCodelabCreationDate() {
        return codelabCreationDate;
    }

    public String getCodelabComment() {
        return codelabComment;
    }

    public String getCodelabSolutionUrl() {
        return codelabSolutionUrl;
    }

    public boolean codelabCompleted() {
        return codelabCompleted;
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
                ", codelabCreationDate=" + codelabCreationDate +
                ", codelabComment='" + codelabComment + '\'' +
                ", codelabSolutionUrl='" + codelabSolutionUrl + '\'' +
                ", codelabCompleted=" + codelabCompleted +
                '}';
    }
}
