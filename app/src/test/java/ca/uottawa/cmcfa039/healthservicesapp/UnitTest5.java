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

public class UnitTest5 {
    final User user = new User ("Wassim","coolguy","tutorialTA","coolguywassim@gmail.com");

    @Test
    public void getters() {
        String actual = user.getFirstName();
        String expected = "Wassim";
        assertEquals(expected, actual);

        actual = user.getLastName();
        expected = "coolguy";
        assertEquals(expected, actual);

        actual = user.getPassword();
        expected = "tutorialTA";
        assertEquals(expected, actual);

        actual = user.getEmail();
        expected = "coolguywassim@gmail.com";
        assertEquals(expected, actual);
    }
}
