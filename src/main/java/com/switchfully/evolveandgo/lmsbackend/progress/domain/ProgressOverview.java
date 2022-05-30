package com.switchfully.evolveandgo.lmsbackend.progress.domain;


public class ProgressOverview {
//
//    Long getStudentId();
//    Integer getNumberOfCompletedCodelabs();

    private final Long studentId;
    private final Long numberOfCompletedCodelabs;

    public ProgressOverview(Long studentId, Long numberOfCompletedCodelabs) {
        this.studentId = studentId;
        this.numberOfCompletedCodelabs = numberOfCompletedCodelabs;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getNumberOfCompletedCodelabs() {
        return numberOfCompletedCodelabs;
    }

    @Override
    public String toString() {
        return "ProgressOverview{" +
                "studentId=" + studentId +
                ", numberOfCompletedCodelabs=" + numberOfCompletedCodelabs +
                '}';
    }
}
