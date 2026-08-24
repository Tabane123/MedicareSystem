/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicare;

/**
 *
 * @author lenovo
 */public class Bed {
    private String bedId;
    private boolean occupied;
    private String patientId;

    public Bed(String bedId) {
        this.bedId = bedId;
        this.occupied = false;
        this.patientId = null;
    }

    public String getBedId() {
        return bedId;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public String getPatientId() {
        return patientId;
    }

    // Allocate bed
    public void occupy(String patientId) {
        this.occupied = true;
        this.patientId = patientId;
    }

    // Free bed
    public void release() {
        this.occupied = false;
        this.patientId = null;
    }
}