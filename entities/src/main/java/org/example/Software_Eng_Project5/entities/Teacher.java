package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher implements Serializable {

    @Id
    private String userName;
//    @ManyToMany(mappedBy = "teacherList", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Profession.class)
//    private List<Profession> professionList;

    public Teacher() {
    }

    public Teacher(String userName,  List<Profession> professions) {
        this.userName = userName;
//        for(Profession profession: professions){
//            profession.getTeacherList().add(this);
//        }
//        setProfessionList(professions);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

//    public List<Profession> getProfessionList() {
//        return professionList;
//    }
//
//    public void setProfessionList(List<Profession> professionList) {
//        this.professionList = professionList;
//    }
}
