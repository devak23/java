package com.ak.learnings;

import java.util.Objects;

public class StudentResult {
    private String name;
    private int rank;
    private int marks;
    private String percentage;

    public static StudentResult initialize(String[] elements) {
        if (elements == null || elements.length != 4) {
            return null;
        }
        return new StudentResult()
                .setRank(Integer.parseInt(elements[0]))
                .setName(elements[1])
                .setMarks(Integer.parseInt(elements[2]))
                .setPercentage(elements[3]);
    }

    public String getName() {
        return name;
    }

    public StudentResult setName(String name) {
        this.name = name;
        return this;
    }

    public int getRank() {
        return rank;
    }

    public StudentResult setRank(int rank) {
        this.rank = rank;
        return this;
    }

    public int getMarks() {
        return marks;
    }

    public StudentResult setMarks(int marks) {
        this.marks = marks;
        return this;
    }

    public String getPercentage() {
        return percentage;
    }

    public StudentResult setPercentage(String percentage) {
        this.percentage = percentage;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentResult that = (StudentResult) o;
        return rank == that.rank &&
                marks == that.marks &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rank, marks);
    }

    @Override
    public String toString() {
        return "StudentResult{" +
                "name='" + name + '\'' +
                ", rank=" + rank +
                ", marks=" + marks +
                ", percentage='" + percentage + '\'' +
                '}';
    }
}