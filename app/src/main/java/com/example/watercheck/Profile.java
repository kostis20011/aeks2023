package com.example.watercheck;

// Profile.java
public class Profile {
    private int _id;
    private String _name;
    private String _gender;
    private int _age;
    private double _weight;
    private double _height;

    public Profile() {
    }

    public Profile(int _id, String _name, String _gender, int _age, double _weight, double _height) {
        this._id = _id;
        this._name = _name;
        this._gender = _gender;
        this._age = _age;
        this._weight = _weight;
        this._height = _height;
    }

    public Profile(String _name, String _gender, int _age, double _weight, double _height) {
        this._name = _name;
        this._gender = _gender;
        this._age = _age;
        this._weight = _weight;
        this._height = _height;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id=id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name=name;
    }

    public String getGender() {
        return _gender;
    }

    public void setGender(String gender) {
        this._gender=gender;
    }

    public int getAge() {
        return _age;
    }

    public void setAge(int age) { this._age=age; }

    public double getWeight() {
        return _weight;
    }

    public void setWeight(double weight) {
        this._weight=weight;
    }

    public double getHeight() {
        return _height;
    }

    public void setHeight(double height) {
        this._height=height;
    }
}
