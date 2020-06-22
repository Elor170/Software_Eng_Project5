package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int correctAnsNum;
    private String code; // 5 digits
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
            targetEntity = Test.class)
    @JoinTable(name="questions_tests", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private List<Test> testList;


    public Question() {

    }

    public Question(String questionText, String ans1Text, String ans2Text, String ans3Text, String ans4Text
            , int correctAns, Profession profession, List<Course> courses) {
        this.questionText = questionText;
        //TODO change the constructor

        List<Answer> answerList = new ArrayList<>();
        Answer answer1 = new Answer(ans1Text);
        answerList.add(answer1);
        Answer answer2 = new Answer(ans2Text);
        answerList.add(answer2);
        Answer answer3 = new Answer(ans3Text);
        answerList.add(answer3);
        Answer answer4 = new Answer(ans4Text);
        answerList.add(answer4);
        this.setAnswers(answerList);
        this.setProfession(profession);
        this.setCourseList(courses);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
