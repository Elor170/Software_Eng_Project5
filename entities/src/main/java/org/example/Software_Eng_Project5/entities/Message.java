package org.example.Software_Eng_Project5.entities;

import java.io.Serializable;

public class Message implements Serializable {
    private String command;
    private String IndexString;
    private String type;
    private Object objList;


    public Message() {
    }

    public Message(String command, Object objList) {
        this.command = command;
        this.objList = objList;
    }

    public String getIndexString() {
        return IndexString;
    }

    public void setIndexString(String indexString) {
        IndexString = indexString;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObjList() {
        return objList;
    }

    public void setObjList(Object objList) {
        this.objList = objList;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}