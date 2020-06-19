package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "courses")
public class Course implements Serializable {
    @Id
    private String code; // 2 digits
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profession_id")
    private Profession profession;

    public Course() {
    }

    public Course(String code, String name, Profession profession) {
        this.code = code;
        this.name = name;
        this.profession = profession;
        profession.addCourse(this);
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
}
