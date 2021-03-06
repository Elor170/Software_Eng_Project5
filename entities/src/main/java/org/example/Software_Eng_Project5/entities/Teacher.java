package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher implements Serializable {

    @Id
    private String userName;
    @ManyToMany(mappedBy = "teacherList", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Profession.class)
    private List<Profession> professionList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer")
    private List<Question> questionList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer")
    private List<Exam> examList; // adding test

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private List<SolvedExam> solvedExamList;


    public Teacher() {
    }

    public Teacher(String userName,  List<Profession> professions) {
        this.userName = userName;
        setProfessionList(professions);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Profession> getProfessionList() {
        return professionList;
    }

    public void setProfessionList(List<Profession> professionList) {
        this.professionList = professionList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Exam> getExamList() {
        return examList;
    }

    public void addTest(Exam exam){
        this.examList.add(exam);
    }

    public void setExamList(List<Exam> examList)
    {
        this.examList = examList;
    }

    public List<SolvedExam> getSolvedExamList()
    {
        return solvedExamList;
    }

    public void setSolvedExamList(List<SolvedExam> solvedExamList)
    {
        this.solvedExamList = solvedExamList;
    }
}
