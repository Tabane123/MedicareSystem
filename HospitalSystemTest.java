/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.medicare.HospitalSystem;
import com.mycompany.medicare.Inpatient;
import com.mycompany.medicare.Patient;
import com.mycompany.medicare.PatientCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalSystemTest {

    private HospitalSystem hospital;

    // This runs before every single test
    @BeforeEach
    public void setUp() {
        hospital = new HospitalSystem();
    }

    // 1. Register a patient
    @Test
    public void testRegisterPatient() {
        Patient p = new Patient("P001", "John", "Smith", 25, "Male", "Flu", PatientCategory.OUTPATIENT);
        
        boolean result = hospital.registerPatient(p);
        
        assertTrue(result);                               // registration should succeed
        assertEquals(1, hospital.getPatients().size());   // list should now have 1 patient
    }

    // 2. Search for a patient
    @Test
    public void testSearchPatient() {
        Patient p = new Patient("P002", "Sarah", "Jones", 30, "Female", "Cold", PatientCategory.EMERGENCY);
        hospital.registerPatient(p);

        Patient found = hospital.findPatient("P002");
        
        assertNotNull(found);                             // patient should be found
        assertEquals("Sarah", found.getFirstName());
        assertEquals("Jones", found.getLastName());
    }

    // 3. Update patient details
    @Test
    public void testUpdatePatient() {
        Patient p = new Patient("P003", "Mike", "Brown", 40, "Male", "Headache", PatientCategory.OUTPATIENT);
        hospital.registerPatient(p);

        boolean result = hospital.updatePatient("P003", "Michael", "Brown", 41, "Male", "Migraine");
        
        assertTrue(result);

        Patient updated = hospital.findPatient("P003");
        assertEquals("Michael", updated.getFirstName());
        assertEquals(41, updated.getAge());
        assertEquals("Migraine", updated.getMedicalCondition());
    }

    // 4. Delete a patient
    @Test
    public void testDeletePatient() {
        Patient p = new Patient("P004", "Lisa", "White", 22, "Female", "Fever", PatientCategory.EMERGENCY);
        hospital.registerPatient(p);

        boolean result = hospital.deletePatient("P004");
        
        assertTrue(result);
        assertNull(hospital.findPatient("P004"));         // patient should no longer exist
    }

    // 5. Allocate a bed
    @Test
    public void testAllocateBed() {
        Inpatient ip = new Inpatient("P005", "Tom", "Green", 35, "Male", "Broken arm", null, null);
        hospital.registerPatient(ip);

        boolean result = hospital.allocateBed("P005");
        
        assertTrue(result);
        assertTrue(hospital.getBeds()[0].isOccupied());   // first bed should be occupied
        assertEquals("B01", ((Inpatient) hospital.findPatient("P005")).getBedNumber());
    }

    // 6. Release a bed
    @Test
    public void testReleaseBed() {
        Inpatient ip = new Inpatient("P006", "Emma", "Black", 28, "Female", "Infection", null, null);
        hospital.registerPatient(ip);
        hospital.allocateBed("P006");                     // allocates B01

        boolean result = hospital.releaseBed("B01");
        
        assertTrue(result);
        assertFalse(hospital.getBeds()[0].isOccupied());  // bed should now be free
    }

    // 7. Prevent duplicate Patient IDs
    @Test
    public void testPreventDuplicatePatientId() {
        Patient p1 = new Patient("P007", "Alex", "King", 50, "Male", "Diabetes", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P007", "Amy", "Queen", 45, "Female", "Asthma", PatientCategory.EMERGENCY);

        assertTrue(hospital.registerPatient(p1));         // first one succeeds
        assertFalse(hospital.registerPatient(p2));        // second one with same ID fails
    }

    // 8. Prevent allocating an occupied bed (patient already has a bed)
    @Test
    public void testPreventAllocatingOccupiedBed() {
        Inpatient ip = new Inpatient("P008", "David", "Lee", 60, "Male", "Heart problem", null, null);
        hospital.registerPatient(ip);
        hospital.allocateBed("P008");                     // patient now has a bed

        boolean result = hospital.allocateBed("P008");    // try to allocate again
        
        assertFalse(result);                              // should fail
    }

    // 9. Prevent bed allocation when all beds are occupied
    @Test
    public void testPreventAllocationWhenAllBedsOccupied() {
        // Fill all 20 beds
        for (int i = 1; i <= 20; i++) {
            String id = "P" + i;
            Inpatient ip = new Inpatient(id, "Name", "Surname", 30, "Male", "Test", null, null);
            hospital.registerPatient(ip);
            hospital.allocateBed(id);
        }

        // Try to give a bed to one more patient
        Inpatient extra = new Inpatient("P999", "Extra", "Patient", 40, "Female", "Test", null, null);
        hospital.registerPatient(extra);

        boolean result = hospital.allocateBed("P999");
        
        assertFalse(result);                              // should fail – no beds left
    }

    // 10. Sort patients by surname
    @Test
    public void testSortBySurname() {
        hospital.registerPatient(new Patient("P010", "Zara", "Adams", 20, "Female", "Test", PatientCategory.OUTPATIENT));
        hospital.registerPatient(new Patient("P011", "Amy", "Zulu", 22, "Female", "Test", PatientCategory.OUTPATIENT));
        hospital.registerPatient(new Patient("P012", "Ben", "Miller", 25, "Male", "Test", PatientCategory.EMERGENCY));

        hospital.sortBySurname();

        assertEquals("Adams", hospital.getPatients().get(0).getLastName());
        assertEquals("Miller", hospital.getPatients().get(1).getLastName());
        assertEquals("Zulu", hospital.getPatients().get(2).getLastName());
    }

    // 11. Sort patients by Patient ID
    @Test
    public void testSortByPatientId() {
        hospital.registerPatient(new Patient("P030", "Chris", "Young", 33, "Male", "Test", PatientCategory.OUTPATIENT));
        hospital.registerPatient(new Patient("P010", "Dana", "Old", 44, "Female", "Test", PatientCategory.EMERGENCY));
        hospital.registerPatient(new Patient("P020", "Eve", "Middle", 28, "Female", "Test", PatientCategory.OUTPATIENT));

        hospital.sortByPatientId();

        assertEquals("P010", hospital.getPatients().get(0).getPatientId());
        assertEquals("P020", hospital.getPatients().get(1).getPatientId());
        assertEquals("P030", hospital.getPatients().get(2).getPatientId());
    }
}