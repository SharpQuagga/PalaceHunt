package com.mycompany.secondproject.caterers;

public class Caterer {
    public String id;
    public String name;
    public String address;
    public String phone;
    public String booked;
    public String city;
    public String email;
    public String password;

    Caterer(){

    }

    public Caterer(String id, String name, String address, String phone, String booked, String city, String email, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.booked = booked;
        this.city = city;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getBooked() {
        return booked;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
