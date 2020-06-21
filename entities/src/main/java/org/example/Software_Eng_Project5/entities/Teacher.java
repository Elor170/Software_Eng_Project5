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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "writer")
    private List<Question> questionList;

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
//        for (Profession profession: professionList){
//            profession.getTeacherList().add(this);
//        }
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
        for (Question question: questionList){
            question.setWriter(this);
        }
    }
}
