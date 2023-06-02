package com.example.watercheck;

// Profile.java
public class Profile {
    private int id;
    private String name;
    private String gender;
    private int age;
    private double weight;
    private double height;

    public Profile() {
    }

    public Profile(int id, String name, String gender, int age, double weight, double height) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender=gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) { this.age=age; }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight=weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height=height;
    }
}
