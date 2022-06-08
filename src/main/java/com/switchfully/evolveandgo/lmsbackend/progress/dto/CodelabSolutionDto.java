package com.switchfully.evolveandgo.lmsbackend.progress.dto;

import java.util.Objects;

public class CodelabSolutionDto {
    private final String studentDisplayName;
    private final String solutionUrl;

    public CodelabSolutionDto(String studentDisplayName, String solutionUrl) {
        this.studentDisplayName = studentDisplayName;
        this.solutionUrl = solutionUrl;
    }

    public String getStudentDisplayName() {
        return studentDisplayName;
    }

    public String getSolutionUrl() {
        return solutionUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodelabSolutionDto that = (CodelabSolutionDto) o;
        return Objects.equals(studentDisplayName, that.studentDisplayName) && Objects.equals(solutionUrl, that.solutionUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentDisplayName, solutionUrl);
    }

    @Override
    public String toString() {
        return "CodelabSolutionDto{" +
                "studentDisplayName='" + studentDisplayName + '\'' +
                ", solutionUrl='" + solutionUrl + '\'' +
                '}';
    }
}
