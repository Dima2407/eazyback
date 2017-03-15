package com.kaa_solutions.eazyback.models;


public class Contact implements Comparable<Contact> {

    private int id;
    private String name;
    private String phone;
    private String timeLastDelayedCall;
    private String additionalNumber;

    public Contact() {
    }

    public String getAdditionalNumber() {
        return additionalNumber;
    }

    public void setAdditionalNumber(String additionalNumber) {
        this.additionalNumber = additionalNumber;
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

    public String getTimeLastDelayedCall() {
        return timeLastDelayedCall;
    }

    public void setTimeLastDelayedCall(String timeLastDelayedCall) {
        this.timeLastDelayedCall = timeLastDelayedCall;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", timeLastDelayedCall='" + timeLastDelayedCall + '\'' +
                ", additionalNumber='" + additionalNumber + '\'' +
                '}';
    }

    @Override
    public int compareTo(Contact another) {
        return name.compareTo(another.name);
    }
}
