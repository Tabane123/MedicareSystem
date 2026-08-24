/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicare;

/**
 *
 * @author lenovo
 */
public class Inpatient extends Patient {
   String wardNumber;
   String bedNumber;

    // Constructor
    public Inpatient(String patientId, String firstName, String lastName, int age,
                     String gender, String medicalCondition,
                     String wardNumber, String bedNumber) {

        // Call parent constructor
        super(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);

        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    // Getters and Setters
    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    // Override display method
    @Override
    public void displayDetails() {
        super.displayDetails();   // show normal details first
        System.out.println("Ward Number       : " + wardNumber);
        System.out.println("Bed Number        : " + bedNumber);
    }
}