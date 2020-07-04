package org.example.Software_Eng_Project5.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pulled_exam")
public class PulledExam implements Serializable {

    @Id
    private String executionCode;
    private String teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "original_exam")
    private Exam originalExam;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pulledExam", targetEntity = SolvedExam.class)
//    @JoinColumn(name = "pulled_exam")
    private List<SolvedExam> solvedExams;

    public PulledExam(Exam originalExam) {
        this.originalExam = originalExam;
    }

    public PulledExam() { }

    public String getExecutionCode() { return executionCode; }

    public void setExecutionCode(String executionCode) { this.executionCode = executionCode; }

    public Exam getOriginalExam() { return originalExam; }

    public void setOriginalExam(Exam originalExam) { this.originalExam = originalExam; }

    public String getTeacher()
    {
        return teacher;
    }

    public void setTeacher(String teacher)
    {
        this.teacher = teacher;
    }

    public List<SolvedExam> getSolvedExams()
    {
        return solvedExams;
    }

    public void setSolvedExams(List<SolvedExam> solvedExams)
    {
        this.solvedExams = solvedExams;
    }
}
