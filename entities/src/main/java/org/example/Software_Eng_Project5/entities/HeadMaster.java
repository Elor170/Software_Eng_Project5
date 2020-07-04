package org.example.Software_Eng_Project5.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "headmaster")
public class HeadMaster {

    @Id
    private String name;

//    private List<Question> questionsList;
//    private List<Exam> examList;
//    private List<SolvedExam> solvedExamList;

    public HeadMaster(String name) {
        this.name = name;
    }

    public HeadMaster() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<SolvedExam> getSolvedExamList() {
//        return solvedExamList;
//    }
//
//    public void setSolvedExamList(List<SolvedExam> solvedExamList) {
//        this.solvedExamList = solvedExamList;
//    }
//
//    public List<Question> getQuestionsList()
//    {
//        return questionsList;
//    }
//
//    public void setQuestionsList(List<Question> questionsList)
//    {
//        this.questionsList = questionsList;
//    }
//
//    public List<Exam> getExamList()
//    {
//        return examList;
//    }
//
//    public void setExamList(List<Exam> examList)
//    {
//        this.examList = examList;
//    }
}
