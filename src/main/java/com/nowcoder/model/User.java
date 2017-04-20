package com.nowcoder.model;

/**
 * Created by Administrator on 2017/4/20.
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(){

    }
    public User(String name){
        this.name = name;
    }
    public String getDescription(){
        return "name is "+name;
    }
}
