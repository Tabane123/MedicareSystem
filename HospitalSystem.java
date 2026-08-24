/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicare;

/**
 *
 * @author lenovo
 */
import java.util.ArrayList;

public class HospitalSystem {

   ArrayList<Patient> patients;
     Bed[] beds;
   
     
     final String WARD = "Ward-A";

    public HospitalSystem() {
        patients = new ArrayList<>();
        beds = new Bed[20];

        // Create beds B01 to B20
        for (int i = 0; i < 20; i++) {
            String id = "B" + String.format("%02d", (i + 1));
            beds[i] = new Bed(id);
        }
    }

    // ========== PATIENT METHODS ==========

    public boolean registerPatient(Patient p) {
        if (findPatient(p.getPatientId()) != null) {
            System.out.println("Error: Patient ID already exists!");
            return false;
        }
        patients.add(p);
        System.out.println("Patient registered successfully.");
        return true;
    }

    public Patient findPatient(String id) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientId().equalsIgnoreCase(id)) {
                return patients.get(i);
            }
        }
        return null;
    }

    public boolean updatePatient(String id, String firstName, String lastName,
                                 int age, String gender, String condition) {
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return false;
        }
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setAge(age);
        p.setGender(gender);
        p.setMedicalCondition(condition);
        System.out.println("Patient updated successfully.");
        return true;
    }

    public boolean deletePatient(String id) {
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return false;
        }

        // Free the bed if this is an inpatient
        if (p instanceof Inpatient) {
            Inpatient ip = (Inpatient) p;
            if (ip.getBedNumber() != null) {
                releaseBed(ip.getBedNumber());
            }
        }

        patients.remove(p);
        System.out.println("Patient deleted successfully.");
        return true;
    }

    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        System.out.println("\n===== ALL PATIENTS =====");
        for (int i = 0; i < patients.size(); i++) {
            patients.get(i).displayDetails();
        }
    }
    // ========== SORTING METHODS ==========

public void sortBySurname() {
    for (int i = 0; i < patients.size() - 1; i++) {
        for (int j = 0; j < patients.size() - i - 1; j++) {
            if (patients.get(j).getLastName().compareToIgnoreCase(patients.get(j + 1).getLastName()) > 0) {
                // Swap the two patients
                Patient temp = patients.get(j);
                patients.set(j, patients.get(j + 1));
                patients.set(j + 1, temp);
            }
        }
    }
}

public void sortByPatientId() {
    for (int i = 0; i < patients.size() - 1; i++) {
        for (int j = 0; j < patients.size() - i - 1; j++) {
            if (patients.get(j).getPatientId().compareToIgnoreCase(patients.get(j + 1).getPatientId()) > 0) {
                // Swap the two patients
                Patient temp = patients.get(j);
                patients.set(j, patients.get(j + 1));
                patients.set(j + 1, temp);
            }
        }
    }
}

// ========== GETTERS (needed for the tests) ==========

public java.util.ArrayList<Patient> getPatients() {
    return patients;
}

public Bed[] getBeds() {
    return beds;
}

    // ========== BED METHODS ==========

    public boolean allocateBed(String patientId) {
        Patient p = findPatient(patientId);

        if (p == null) {
            System.out.println("Patient not found.");
            return false;
        }

        if (p.getCategory() != PatientCategory.INPATIENT) {
            System.out.println("Only Inpatients can be allocated a bed.");
            return false;
        }

        if (p instanceof Inpatient) {
            Inpatient ip = (Inpatient) p;
            if (ip.getBedNumber() != null) {
                System.out.println("This patient already has a bed.");
                return false;
            }
        }

        // Find first free bed
        for (int i = 0; i < beds.length; i++) {
            if (!beds[i].isOccupied()) {
                beds[i].occupy(patientId);

                Inpatient ip = (Inpatient) p;
                ip.setWardNumber(WARD);
                ip.setBedNumber(beds[i].getBedId());

                System.out.println("Bed " + beds[i].getBedId() + " allocated successfully.");
                return true;
            }
        }

        System.out.println("Sorry, no beds available.");
        return false;
    }

    public boolean releaseBed(String bedId) {
        for (int i = 0; i < beds.length; i++) {
            if (beds[i].getBedId().equalsIgnoreCase(bedId)) {
                if (!beds[i].isOccupied()) {
                    System.out.println("This bed is already free.");
                    return false;
                }

                String patientId = beds[i].getPatientId();
                beds[i].release();

                Patient p = findPatient(patientId);
                if (p instanceof Inpatient) {
                    Inpatient ip = (Inpatient) p;
                    ip.setBedNumber(null);
                    ip.setWardNumber(null);
                }

                System.out.println("Bed " + bedId + " has been released.");
                return true;
            }
        }
        System.out.println("Bed not found.");
        return false;
    }

    public void displayWardLayout() {
        System.out.println("\n===== WARD LAYOUT (4 x 5) =====");
        for (int i = 0; i < 20; i++) {
            if (beds[i].isOccupied()) {
                System.out.print(beds[i].getBedId() + " [Occupied]   ");
            } else {
                System.out.print(beds[i].getBedId() + " [Available]  ");
            }
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

    public void displayOccupiedBeds() {
        System.out.println("\n===== OCCUPIED BEDS =====");
        boolean found = false;
        for (int i = 0; i < beds.length; i++) {
            if (beds[i].isOccupied()) {
                System.out.println(beds[i].getBedId() + " - Patient: " + beds[i].getPatientId());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No beds are currently occupied.");
        }
    }

    public void displayAvailableBeds() {
        System.out.println("\n===== AVAILABLE BEDS =====");
        boolean found = false;
        for (int i = 0; i < beds.length; i++) {
            if (!beds[i].isOccupied()) {
                System.out.println(beds[i].getBedId());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available beds.");
        }
    }

    // ========== REPORTS ==========

    public void generateReports() {
        System.out.println("\n========== HOSPITAL REPORTS ==========");

        displayAllPatients();
        displayAvailableBeds();
        displayOccupiedBeds();

        int totalPatients = patients.size();
        int occupiedCount = 0;

        for (int i = 0; i < beds.length; i++) {
            if (beds[i].isOccupied()) {
                occupiedCount++;
            }
        }

        double percentage = (occupiedCount / 20.0) * 100;

        System.out.println("\n----- SUMMARY -----");
        System.out.println("Total Registered Patients : " + totalPatients);
        System.out.println("Total Occupied Beds       : " + occupiedCount);
        System.out.printf("Bed Occupancy Percentage  : %.1f%%\n", percentage);
    }
}
  
 