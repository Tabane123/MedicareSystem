/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.medicare;

/**
 *
 * @author lenovo
 */
 import java.util.*;
public class Medicare {


    public static HospitalSystem hospital = new HospitalSystem();
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            showMenu();
            choice = getNumber("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    searchPatient();
                    break;
                case 3:
                    updatePatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    hospital.displayAllPatients();
                    break;
                case 6:
                    allocateBed();
                    break;
                case 7:
                    releaseBed();
                    break;
                case 8:
                    hospital.displayWardLayout();
                    break;
                case 9:
                    hospital.displayOccupiedBeds();
                    break;
                case 10:
                    hospital.generateReports();
                    break;
                case 0:
                    System.out.println("Thank you for using the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        input.close();
    }

    private static void showMenu() {
        System.out.println("\n==============================================");
        System.out.println("     MEDCARE HOSPITAL ADMISSION SYSTEM");
        System.out.println("==============================================");
        System.out.println("1. Register New Patient");
        System.out.println("2. Search Patient by ID");
        System.out.println("3. Update Patient Details");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.println("6. Allocate Bed (Inpatient only)");
        System.out.println("7. Release Bed");
        System.out.println("8. Display Ward Layout");
        System.out.println("9. Display Occupied Beds");
        System.out.println("10. Generate Reports");
        System.out.println("0. Exit");
        System.out.println("==============================================");
    }

    private static void registerPatient() {
        System.out.println("\n--- Register New Patient ---");

        String id = getText("Patient ID: ");
        String firstName = getText("First Name: ");
        String lastName = getText("Last Name: ");
        int age = getNumber("Age: ");
        String gender = getText("Gender: ");
        String condition = getText("Medical Condition: ");

        System.out.println("Choose Category:");
        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");
        int catChoice = getNumber("Enter choice: ");

        Patient patient = null;

        if (catChoice == 1) {
            patient = new Inpatient(id, firstName, lastName, age, gender, condition, null, null);
        } else if (catChoice == 2) {
            patient = new Patient(id, firstName, lastName, age, gender, condition, PatientCategory.OUTPATIENT);
        } else if (catChoice == 3) {
            patient = new Patient(id, firstName, lastName, age, gender, condition, PatientCategory.EMERGENCY);
        } else {
            System.out.println("Invalid category choice.");
            return;
        }

        hospital.registerPatient(patient);
    }

    private static void searchPatient() {
        String id = getText("Enter Patient ID: ");
        Patient p = hospital.findPatient(id);
        if (p != null) {
            p.displayDetails();
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void updatePatient() {
        String id = getText("Enter Patient ID to update: ");
        String firstName = getText("New First Name: ");
        String lastName = getText("New Last Name: ");
        int age = getNumber("New Age: ");
        String gender = getText("New Gender: ");
        String condition = getText("New Medical Condition: ");

        hospital.updatePatient(id, firstName, lastName, age, gender, condition);
    }

    private static void deletePatient() {
        String id = getText("Enter Patient ID to delete: ");
        hospital.deletePatient(id);
    }

    private static void allocateBed() {
        String id = getText("Enter Patient ID: ");
        hospital.allocateBed(id);
    }

    private static void releaseBed() {
        String bedId = getText("Enter Bed ID (example: B05): ");
        hospital.releaseBed(bedId);
    }

    // Helper methods
    public static String getText(String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }

   public static int getNumber(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
   

