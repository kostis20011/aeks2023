package com.example.watercheck;

public class Profile {
    private int id;
    private String name;
    private double height;
    private double weight;
    private int age;
    private String gender;

    public Profile(String name, double height, double weight, int age, String gender) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }

    public Profile(int id, String name, double height, double weight, int age, String gender) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
