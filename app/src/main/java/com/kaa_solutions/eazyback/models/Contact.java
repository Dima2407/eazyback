package com.kaa_solutions.eazyback.models;


public class Contact {

    private int id;
    private String name;
    private String phone;


    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact: id: " + id + ", name: " + name + ", phone: " + phone;
    }


}
