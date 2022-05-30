package com.switchfully.evolveandgo.lmsbackend.progress.domain;

public interface ProgressOverview {

    Long getStudentId();
    Integer getNumberOfCompletedCodelabs();

//    private final Long studentId;
//    private final int numberOfCompletedCodelabs;

//    public ProgressOverview(Long studentId, int numberOfCompletedCodelabs) {
//        this.studentId = studentId;
//        this.numberOfCompletedCodelabs = numberOfCompletedCodelabs;
//    }
//
//    public Long getStudentId() {
//        return studentId;
//    }
//
//    public int getNumberOfCompletedCodelabs() {
//        return numberOfCompletedCodelabs;
//    }
//
//    @Override
//    public String toString() {
//        return "ProgressOverview{" +
//                "studentId=" + studentId +
//                ", numberOfCompletedCodelabs=" + numberOfCompletedCodelabs +
//                '}';
//    }
}
