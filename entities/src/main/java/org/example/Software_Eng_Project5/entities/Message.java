package org.example.Software_Eng_Project5.entities;

import java.io.Serializable;

public class Message implements Serializable {
    private int indexInt;
    private int testTime;
    private int grade;
    private boolean isList;
    private boolean isManual;
    private String command;
    private String indexString;
    private String type;
    private String itemsType;
    private Object objList;
    private Object SingleObject;
    private Object objList2;
    private Object objList3;
    private Object SingleObject2;
    private Class<?> classType;


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

    public Class<?> getClassType() {
        return classType;
    }

    public void setClassType(Class<?> classType) {
        this.classType = classType;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        this.isList = list;
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

    public int getTestTime()
    {
        return testTime;
    }

    public void setTestTime(int testTime)
    {
        this.testTime = testTime;
    }

    public boolean isManual()
    {
        return isManual;
    }

    public void setManual(boolean manual)
    {
        isManual = manual;
    }

    public Object getObjList3()
    {
        return objList3;
    }

    public void setObjList3(Object objList3)
    {
        this.objList3 = objList3;
    }

    public int getGrade()
    {
        return grade;
    }

    public void setGrade(int grade)
    {
        this.grade = grade;
    }
}