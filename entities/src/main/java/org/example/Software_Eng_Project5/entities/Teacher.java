package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher implements Serializable {

    @Id
    private String userName;

    public Teacher() {
    }

    public Teacher(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
