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

import java.util.ArrayList;

public class Deliverable3UnitTest2 {

    private ArrayList<String> paymentTypes;
    final Employee newEmployee = new Employee ("John", "Lennon", "abc123", "johnlennon@gmail.com", paymentTypes,
            "123 Lennon Street", "Lennon Memorial Hospital", "5197469444");

    @Test
    public void testNewEmployeeClass (){
        String actual = newEmployee.getFirstName();
        String expected = "John";
        assertEquals(expected, actual);

        actual = newEmployee.getLastName();
        expected = "Lennon";
        assertEquals(expected, actual);

        actual = newEmployee.getPassword();
        expected = "abc123";
        assertEquals(expected, actual);

        actual = newEmployee.getEmail();
        expected = "johnlennon@gmail.com";
        assertEquals(expected, actual);

        actual = newEmployee.getAddress();
        expected = "123 Lennon Street";
        assertEquals(expected, actual);

        actual = newEmployee.getClinicName();
        expected = "Lennon Memorial Hospital";
        assertEquals(expected, actual);

        actual = newEmployee.getPhoneNumber();
        expected = "5197469444";
        assertEquals(expected, actual);

        ArrayList<String> Actual = newEmployee.getPaymentTypes();
        ArrayList<String> Expected = paymentTypes;
        assertEquals(Expected, Actual);
    }

}