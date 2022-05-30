package com.switchfully.evolveandgo.lmsbackend.progress.dto;

import java.util.Objects;

public class ProgressOverviewDto {
    private final Long studentId;
    private final String studentName;
    private final int numberOfCompletedCodelabs;
    private final int totalNumberOfCodelabs;

    public ProgressOverviewDto(Long studentId, String studentName, int numberOfCompletedCodelabs, int totalNumberOfCodelabs) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.numberOfCompletedCodelabs = numberOfCompletedCodelabs;
        this.totalNumberOfCodelabs = totalNumberOfCodelabs;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getTotalNumberOfCodelabs() {
        return totalNumberOfCodelabs;
    }

    public int getNumberOfCompletedCodelabs() {
        return numberOfCompletedCodelabs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgressOverviewDto that = (ProgressOverviewDto) o;
        return totalNumberOfCodelabs == that.totalNumberOfCodelabs && numberOfCompletedCodelabs == that.numberOfCompletedCodelabs && Objects.equals(studentId, that.studentId) && Objects.equals(studentName, that.studentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName, totalNumberOfCodelabs, numberOfCompletedCodelabs);
    }
}
