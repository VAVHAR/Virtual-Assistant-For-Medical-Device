package com.example.startup.Models;

public class Model {



    private String Address;
    private String Contact_No;
    private String Name;
    private String Disease_Id;
    private String Symptoms;
    private String Disease;


    public String getDisease_Id() {
        return Disease_Id;
    }

    public void setDisease_Id(String Disease_Id) {
        this.Disease_Id = Disease_Id;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String Symptoms) {
        this.Symptoms = Symptoms;
    }

    public String getDisease() {
        return Disease;
    }

    public void setDisease(String Disease) {
        this.Disease = Disease;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getContact_No() {
        return Contact_No;
    }

    public void setContact_No(String Contact_No) {
        this.Contact_No = Contact_No;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }



}