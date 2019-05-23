package com.mytest.demo.Model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Columns implements Serializable {

    private String Field;
    private String Type;
    private String Comment;
    private String Key;

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

}
