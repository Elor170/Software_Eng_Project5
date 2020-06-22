package org.example.Software_Eng_Project5.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table; //wtf???

@Entity
@Table(name = "test")
public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private int testTime;
    private String textForTeacher;
    private String textForStudent;
    private boolean isManual; //is the test manual

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_teacher_id")
    private Teacher author;

    @ManyToMany(mappedBy = "testList", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Question.class)
    private List<Question> questionList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_profession_id")
    private Profession profession;

    public Test(String code, int testTime, boolean isManual, Teacher author,
                Course course, Profession profession) {
        this.code = code;
        this.testTime = testTime;
        this.isManual = isManual;
        this.author = author;
        this.course = course;
        this.profession = profession;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public int getTestTime() { return testTime; }

    public void setTestTime(int testTime) { this.testTime = testTime; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public Profession getProfession() { return profession; }

    public void setProfession(Profession profession) { this.profession = profession; }

    public String getTextForTeacher() { return textForTeacher; }

    public void setTextForTeacher(String textForTeacher) { this.textForTeacher = textForTeacher; }

    public String getTextForStudent() { return textForStudent; }

    public void setTextForStudent(String textForStudent) { this.textForStudent = textForStudent; }

    public Teacher getAuthor() { return author; }

    public void setAuthor(Teacher author) { this.author = author; }

    public List<Question> getQuestionList() { return questionList; }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
