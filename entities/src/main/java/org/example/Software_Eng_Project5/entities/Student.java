package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "students")
public class Student implements Serializable {

    @Id
    private String userName;

    private String identification;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private List<SolvedExam> solvedExamList;

    public Student() {
    }

    public Student(String userName) {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getIdentification()
    {
        return identification;
    }

    public void setIdentification(String identification)
    {
        this.identification = identification;
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
