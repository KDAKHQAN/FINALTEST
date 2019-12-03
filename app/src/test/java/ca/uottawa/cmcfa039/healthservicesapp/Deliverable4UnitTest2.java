package ca.uottawa.cmcfa039.healthservicesapp;

import android.widget.Toast;

import androidx.annotation.NonNull;
import static org.junit.Assert.*;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;


public class Deliverable4UnitTest2 {
    final Patient newPatient = new Patient ("John","Doe","john336","johndoe@gmail.com");
    final Employee newEmployee = new Employee("John", "Doe", "john336", "johndoe@gmail.com");
    final Service newService = new Service ("blood test", "John Lennon");
    final Appointment newAppointment = new Appointment (newEmployee,newPatient,newService,"October 5");

    @Test
    public void testAppointment (){
        Employee actual = newAppointment.getAssignedEmployee();
        Employee expected = newEmployee;
        assertEquals(expected, actual);

        Patient Actual = newAppointment.getAppointmentPatient();
        Patient Expected = newPatient;
        assertEquals(Expected, Actual);

        Service actualService = newAppointment.getAppointmentService();
        Service expectedService = newService;
        assertEquals(expectedService, actualService);

        String actualDate = newAppointment.getAppointmentDate();
        String expectedDate = "October 5";
        assertEquals(expectedDate, actualDate);
    }

}
