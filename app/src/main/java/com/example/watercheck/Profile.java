package com.example.watercheck;

// Profile.java
public class Profile {
    private int id;
    private String gender;
    private int age;
    private double weight;
    private double height;

    public Profile(int id, String gender, int age, double weight, double height) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }
}
