package org.example.Software_Eng_Project5.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "solved_exam")
public class SolvedExam implements Serializable{

    @Id
    private String id;
    private int time;
    private String teacherComments;
    private int grade;

    private PulledExam pulledExam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solvedExam", targetEntity = StudentAns.class)
    private List<Integer> studentAns;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student")
    private Student student;


    public SolvedExam(String id, PulledExam pulledExam, List<Integer> studentAns, int time, Student student, int grade) {
        this.id = id;
        this.pulledExam = pulledExam;
        this.studentAns = studentAns;
        this.time = time;
        this.student = student;
        this.grade = grade;
    }

    public SolvedExam(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PulledExam getPulledExam() {
        return pulledExam;
    }

    public void setPulledExam(PulledExam pulledExam) {
        this.pulledExam = pulledExam;
    }

    public List<Integer> getStudentAns() {
        return studentAns;
    }

    public void setStudentAns(List<Integer> studentAns) {
        this.studentAns = studentAns;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTeacherComments() {
        return teacherComments;
    }

    public void setTeacherComments(String teacherComments) {
        this.teacherComments = teacherComments;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
