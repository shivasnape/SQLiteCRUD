package com.example.android.sqlitecrud.model;

/**
 * Created by wesix on 15/12/17.
 */

public class UserDataList {

    int id;
    String name, dept, joiningdate;
    double salary;

    public UserDataList(int id, String name, String dept, String joiningdate, double salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.joiningdate = joiningdate;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getJoiningdate() {
        return joiningdate;
    }

    public double getSalary() {
        return salary;
    }
}

