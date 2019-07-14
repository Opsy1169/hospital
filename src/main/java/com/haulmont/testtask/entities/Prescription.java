package com.haulmont.testtask.entities;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "prescription", schema = "PUBLIC", catalog = "PUBLIC")
public class Prescription {
    private int id;
    private String description;
    private LocalDate beginDate;
    private int validityInDays;
    private Patient patient;
    private Doctor doctor;
    private Priority priority;



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "beg_date")
    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    @Column(name = "validity_in_days")
    public int getValidityInDays() {
        return validityInDays;
    }

    public void setValidityInDays(int validityInDays) {
        this.validityInDays = validityInDays;
    }

    @OneToOne
    @JoinColumn(name = "patient")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @OneToOne
    @JoinColumn(name = "doctor")
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "priority")
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", beginDate=" + beginDate +
                ", validityInDays=" + validityInDays +
                ", patient=" + patient +
                ", doctor=" + doctor +
                '}';
    }

}
