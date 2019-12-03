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

public class Deliverable3UnitTest1 {

    final Service newService = new Service ("blood test", "John Lennon");

    @Test
    public void testService (){
        String actual = newService.getName();
        String expected = "blood test";
        assertEquals(expected, actual);

        actual = newService.getWorker();
        expected = "John Lennon";
        assertEquals(expected, actual);

        Boolean Actual = newService.equals(newService);
        Boolean Expected = true;
        assertEquals(Expected, Actual);
    }

}
