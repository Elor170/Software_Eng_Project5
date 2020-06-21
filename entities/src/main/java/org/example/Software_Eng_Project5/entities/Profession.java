package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "professions")
public class Profession implements Serializable {
    @Id
    private String code; // 2 digits
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profession", targetEntity = Course.class)
    private List<Course> courseList;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Teacher.class)
    @JoinTable(name="professions_teachers", joinColumns = @JoinColumn(name = "profession_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<Teacher> teacherList;

    @OneToMany(mappedBy = "profession", targetEntity = Question.class)
    private List<Question> questionList;

    public Profession() {
    }

    public Profession(String code, String name) {
        this.code = code;
        this.name = name;
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

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
        for (Course course: courseList){
            course.setProfession(this);
        }
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
        for (Teacher teacher: teacherList){
            teacher.getProfessionList().add(this);
        }
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
        for(Question question: questionList){
            question.setProfession(this);
        }
    }
}
