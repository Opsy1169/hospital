package com.haulmont.testtask.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name ="doctor", schema = "public" )
public class Doctor {
    private int id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String specialization;

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

    @Column(name = "specialization")
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    @Override
    public String toString() {
        return getSecondName() + " " + getFirstName().charAt(0) + ". " + getThirdName().charAt(0) + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id &&
                Objects.equals(firstName, doctor.firstName) &&
                Objects.equals(secondName, doctor.secondName) &&
                Objects.equals(thirdName, doctor.thirdName) &&
                Objects.equals(specialization, doctor.specialization);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, secondName, thirdName, specialization);
    }
}
