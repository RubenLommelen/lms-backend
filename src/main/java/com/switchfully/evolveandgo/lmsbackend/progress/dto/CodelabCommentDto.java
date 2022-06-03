package com.switchfully.evolveandgo.lmsbackend.progress.dto;

public class CodelabCommentDto {
    private final String comment;
    private final Long codelabId;
    private final Long studentId;

    public CodelabCommentDto(String comment, Long codelabId, Long studentId) {
        this.comment = comment;
        this.codelabId = codelabId;
        this.studentId = studentId;
    }

    public String getComment() {
        return comment;
    }

    public Long getCodelabId() {
        return codelabId;
    }

    public Long getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return "CodelabCommentDto{" +
                "comment='" + comment + '\'' +
                ", codelabId=" + codelabId +
                ", studentId=" + studentId +
                '}';
    }
}
