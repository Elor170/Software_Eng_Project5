package org.example.Software_Eng_Project5.entities;

import java.io.Serializable;

public class Message implements Serializable {
    private int indexInt;
    private boolean isList;
    private String command;
    private String indexString;
    private String type;
    private String itemsType;
    private Object objList;
    private Object SingleObject;
    private Object objList2;
    private Object SingleObject2;
    private Class classType;


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

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public String getItemsType() {
        return itemsType;
    }

    public void setItemsType(String itemsType) {
        this.itemsType = itemsType;
    }

    public Object getObjList2() {
        return objList2;
    }

    public void setObjList2(Object objList2) {
        this.objList2 = objList2;
    }

    public Object getSingleObject2() {
        return SingleObject2;
    }

    public void setSingleObject2(Object singleObject2) {
        SingleObject2 = singleObject2;
    }
}