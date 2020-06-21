package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course implements Serializable {
    @Id
    private String code; // 2 digits
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Profession.class)
    @JoinColumn(name = "profession_id")
    private Profession profession;
    @ManyToMany(mappedBy = "courseList", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Question.class)
    private List<Question> questionList;

    public Course() {
    }

    public Course(String code, String name, Profession profession) {
        this.code = code;
        this.name = name;
        this.profession = profession;
        profession.getCourseList().add(this);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
        profession.getCourseList().add(this);
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
        for(Question question: questionList){
            question.getCourseList().add(this);
        }
    }
}
