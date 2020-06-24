package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
    @Id
    private String code; // 5 digits
    private int correctAnsNum;
    private boolean isUsed;

    private String questionText;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private List<Answer> answers; // 4 answers

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profession_id")
    private Profession profession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher writer;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Course.class)
    @JoinTable(name="questions_courses", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courseList;



    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Exam.class)
    @JoinTable(name="questions_tests", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private List<Exam> examList;


    public Question() {

    }

    public Question(String questionText, int correctAns, Teacher writer, String code
            , Profession profession, List<Course> courseList, List<Answer> answerList) {
        this.questionText = questionText;
        this.code = code;
        this.correctAnsNum = correctAns;
        this.writer = writer;
        this.setAnswers(answerList);
        this.profession = profession;
        this.courseList = courseList;
        isUsed = false;
    }

    public int getCorrectAnsNum() {
        return correctAnsNum;
    }

    public void setCorrectAnsNum(int correctAnsNum) {
        this.correctAnsNum = correctAnsNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
        for (Answer answer: answers){
            answer.setQuestion(this);
        }
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Teacher getWriter() {
        return writer;
    }

    public void setWriter(Teacher writer) {
        this.writer = writer;
        writer.getQuestionList().add(this);
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public boolean isUsed()
    {
        return isUsed;
    }

    public void setUsed(boolean used)
    {
        isUsed = used;
    }

    public List<Exam> getExamList()
    {
        return examList;
    }

    public void setExamList(List<Exam> examList)
    {
        this.examList = examList;
    }
}
