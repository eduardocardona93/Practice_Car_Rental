package com.example.practice_car_rental;

public class Car {
    private String make;
    private String name;
    private double dailyRate;
    private double currentMileage;
    private String img;

    public Car(String make, String name, double dailyRate, double currentMileage, String img) {
        this.make = make;
        this.name = name;
        this.dailyRate = dailyRate;
        this.currentMileage = currentMileage;
        this.img = img;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public double getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(double currentMileage) {
        this.currentMileage = currentMileage;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
