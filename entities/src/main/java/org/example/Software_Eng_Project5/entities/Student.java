package org.example.Software_Eng_Project5.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "students")
public class Student implements Serializable {

    @Id
    private String userName;

    public Student() {
    }

    public Student(String userName) {
        this.userName = userName;
    }
}
