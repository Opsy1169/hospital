package com.haulmont.testtask.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "patient", schema = "public")
public class Patient {
    private int id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String phoneNumber;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "firstname")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "secondname")
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Column(name = "thirdname")
    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    @Column(name = "phone")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return  getFirstName() + " " + getSecondName() + " " + getThirdName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id &&
                Objects.equals(firstName, patient.firstName) &&
                Objects.equals(secondName, patient.secondName) &&
                Objects.equals(thirdName, patient.thirdName) &&
                Objects.equals(phoneNumber, patient.phoneNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, secondName, thirdName, phoneNumber);
    }
}
