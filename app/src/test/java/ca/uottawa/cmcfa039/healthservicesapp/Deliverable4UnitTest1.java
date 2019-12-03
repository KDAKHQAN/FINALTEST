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


public class Deliverable4UnitTest1 {
    final Patient newPatient = new Patient ("John","Doe","john336","johndoe@gmail.com");
    final Employee newEmployee = new Employee("John", "Doe", "john336", "johndoe@gmail.com");
    final Review newReview = new Review (newEmployee, newPatient, 5 , "Very good service!");

    @Test
    public void testReview (){
        Employee actual = newReview.getClinic();
        Employee expected = newEmployee;
        assertEquals(expected, actual);

        Patient Actual = newReview.getReviewer();
        Patient Expected = newPatient;
        assertEquals(Expected, Actual);

        int actualRating = newReview.getRating();
        int expectedRating = 5;
        assertEquals(expectedRating, actualRating);

        String actualComment = newReview.getComment();
        String expectedComment = "Very good service!";
        assertEquals(expectedComment, actualComment);
    }

}
