package com.example.myapplication1;

public class CourseInfo {

    public String name;
    public int credit;

    public CourseInfo(){}

    public CourseInfo(String name, int Credit) {
        this.name = name;
        credit = Credit;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }
}
