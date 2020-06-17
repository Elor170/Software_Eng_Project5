package org.example.Software_Eng_Project5.entities;

import java.io.Serializable;

public class Message implements Serializable {
    private int indexInt;
    private String command;
    private String indexString;
    private String type;
    private Object objList;
    private Object SingleObject;


    public Message() {
    }

    public String getIndexString() {
        return indexString;
    }

    public void setIndexString(String indexString) {
        this.indexString = indexString;
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

    public Object getSingleObject() {
        return SingleObject;
    }

    public void setSingleObject(Object singleObject) {
        SingleObject = singleObject;
    }

    public int getIndexInt() {
        return indexInt;
    }

    public void setIndexInt(int indexInt) {
        this.indexInt = indexInt;
    }
}