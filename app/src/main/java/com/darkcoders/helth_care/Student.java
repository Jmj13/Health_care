package com.darkcoders.helth_care;

public class Student {
    public Student(){}
    String password;
    String email;
    String username;
    String color_;
    String today_step;
    String total_step;
    String friend;
    String age;
    String height;
    String weight;
    String gender;
    String calories_gain;
    String total_calories;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getColor_() {
        return color_;
    }

    public void setColor_(String color_) {
        this.color_ = color_;
    }

    public String getToday_step() {
        return today_step;
    }

    public void setToday_step(String today_step) {
        this.today_step = today_step;
    }

    public String getTotal_step() {
        return total_step;
    }

    public void setTotal_step(String total_step) {
        this.total_step = total_step;
    }

    public String getFriend() {
        return friend;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }


    public String getCalories_gain() {
        return calories_gain;
    }

    public void setCalories_gain(String calories_gain) {
        this.calories_gain = calories_gain;
    }

    public String getTotal_calories() {
        return total_calories;
    }

    public void setTotal_calories(String total_calories) {
        this.total_calories = total_calories;
    }

    public Student(String password, String email, String username, String color_, String friend,
                   String today_step, String total_step, String age, String height, String weight, String gender, String calories_gain, String total_calories) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.color_ = color_;
        this.friend = friend;
        this.today_step=today_step;
        this.total_step=total_step;
        this.age=age;
        this.height=height;
        this.weight=weight;
        this.gender=gender;
        this.calories_gain=calories_gain;
        this.total_calories=total_calories;
    }
}
