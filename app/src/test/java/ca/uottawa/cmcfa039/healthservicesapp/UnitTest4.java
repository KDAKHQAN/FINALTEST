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

public class UnitTest4 extends AdminActivity{
    final Admin admin = new Admin ("admin","","5T5ptQ","admin");

    @Test
    public void getters() {
        String actual = admin.getFirstName();
        String expected = "admin";
        assertEquals(expected, actual);

        actual = admin.getLastName();
        expected = "";
        assertEquals(expected, actual);

        actual = admin.getPassword();
        expected = "5T5ptQ";
        assertEquals(expected, actual);

        actual = admin.getEmail();
        expected = "admin";
        assertEquals(expected, actual);
    }

}
