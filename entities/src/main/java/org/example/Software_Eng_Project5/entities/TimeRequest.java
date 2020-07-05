package org.example.Software_Eng_Project5.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "time_request")
public class TimeRequest implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int time;

    private String execCode;

    public TimeRequest(){};

    public TimeRequest(String execCode, int time)
    {
        this.execCode = execCode;
        this.time = time;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public String getExecCode()
    {
        return execCode;
    }

    public void setExecCode(String execCode)
    {
        this.execCode = execCode;
    }
}
