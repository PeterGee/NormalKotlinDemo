package com.example.myapplication.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * @Author qipeng
 * @Date 2023/5/20
 * @Desc greenDao entity
 */
@Entity
public class Note {

    @Id
    private long id;
    @NotNull
    private String text;
    private Date date;

    @Generated(hash = 1899817729)
    public Note(long id, @NotNull String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
