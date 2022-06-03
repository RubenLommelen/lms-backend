package com.switchfully.evolveandgo.lmsbackend.progress.dto;

public class CodelabCommentDto {
    private final String codelabComment;
    private final Long codelabId;
    private final Long studentId;

    public CodelabCommentDto(String codelabComment, Long codelabId, Long studentId) {
        this.codelabComment = codelabComment;
        this.codelabId = codelabId;
        this.studentId = studentId;
    }

    public String getCodelabComment() {
        return codelabComment;
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
                "comment='" + codelabComment + '\'' +
                ", codelabId=" + codelabId +
                ", studentId=" + studentId +
                '}';
    }
}
