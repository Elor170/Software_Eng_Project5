package org.example.Software_Eng_Project5.entities;
import javax.persistence.*;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pulled_exam")
public class PulledExam implements Serializable {

    @Id
    private String executionCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_exam")
    private Exam originalExam;

    public PulledExam(Exam originalExam) {
        this.originalExam = originalExam;
    }

    public PulledExam() { }

    public String getExecutionCode() { return executionCode; }

    public void setExecutionCode(String executionCode) { this.executionCode = executionCode; }

    public Exam getOriginalExam() { return originalExam; }

    public void setOriginalExam(Exam originalExam) { this.originalExam = originalExam; }
}
