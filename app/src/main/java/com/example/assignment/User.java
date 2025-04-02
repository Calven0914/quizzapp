package com.example.assignment;

public class User {
    private String username;
    private String password;

    private Integer marks;

    public User(String username, String password, Integer marks) {
        this.username = username;
        this.password = password;
        this.marks = marks;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", marks=" + marks +
                '}';
    }
}