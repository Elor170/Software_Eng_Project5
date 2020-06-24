package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;

@Entity
@Table(name = "student_ans")
public class StudentAns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SolvedExam.class)
    @JoinColumn(name = "solved_exam")
    private SolvedExam solvedExam;

    public StudentAns() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SolvedExam getSolvedExam() {
        return solvedExam;
    }

    public void setSolvedExam(SolvedExam solvedExam) {
        this.solvedExam = solvedExam;
    }
}
