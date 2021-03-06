package org.example.Software_Eng_Project5.entities;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table; //wtf???

@Entity
@Table(name = "exam")
public class Exam implements Serializable {
    @Id
    private String code;
    private int testTime;
    private String textForTeacher;
    private String textForStudent;
    private boolean isManual; //is the test manual
    private boolean isPulled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_teacher_id")
    private Teacher writer;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "examList",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Question.class)
    private List<Question> questionList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course")
    private Course course;

    private String courseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profession_id")
    private Profession profession;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "originalExam")
    private List<PulledExam> pulledExamList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exam", targetEntity = Grade.class)
    private List<Grade> grades;

    public Exam() {
    }

    public Exam(String code, int testTime, boolean isManual, Teacher writer,
                Profession profession, Course course) {
        this.code = code;
        this.testTime = testTime;
        this.isManual = isManual;
        this.writer = writer;
        this.profession = profession;
        this.course = course;
        this.isPulled = false;
        this.questionList = new ArrayList<>();
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

    public Teacher getAuthor() { return writer; }

    public void setAuthor(Teacher author) { this.writer = author; }

    public List<Question> getQuestionList() { return questionList; }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public boolean isManual() {
        return isManual;
    }

    public void setManual(boolean manual) {
        isManual = manual;
    }

    public boolean isPulled()
    {
        return isPulled;
    }

    public void setPulled(boolean pulled)
    {
        isPulled = pulled;
    }

    public Teacher getWriter()
    {
        return writer;
    }

    public void setWriter(Teacher writer)
    {
        this.writer = writer;
    }

    public List<PulledExam> getPulledExamList()
    {
        return pulledExamList;
    }

    public void setPulledExamList(List<PulledExam> pulledExamList)
    {
        this.pulledExamList = pulledExamList;
    }

    public List<Grade> getGrades()
    {
        return grades;
    }

    public void setGrades(List<Grade> grades)
    {
        this.grades = grades;
        for (Grade grade: grades){
            grade.setExam(this);
        }
    }

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }
}
