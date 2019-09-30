package com.student.usermanagementdemo.entities;

public class GruhUdhyog {

    private String name;
    private String address;
    private String contact;
    private String email;
    private String ownerName;
    private String ownerAddress;

    public GruhUdhyog() {
    }

    public GruhUdhyog(String name, String address, String contact, String email, String ownerName, String ownerAddress) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    @Override
    public String toString() {
        return "GruhUdhyog{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerAddress='" + ownerAddress + '\'' +
                '}';
    }
}
