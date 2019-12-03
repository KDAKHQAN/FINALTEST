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

public class UnitTest1 {


    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    final Employee newClient = new Employee("John", "Doe", "john336", "johndoe@gmail.com");

    @Test
    public void getters() {
        String actual = newClient.getFirstName();
        String expected = "John";
        assertEquals(expected, actual);

        actual = newClient.getLastName();
        expected = "Doe";
        assertEquals(expected, actual);

        actual = newClient.getPassword();
        expected = "john336";
        assertEquals(expected, actual);

        actual = newClient.getEmail();
        expected = "johndoe@gmail.com";
        assertEquals(expected, actual);

    }
}