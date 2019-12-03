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

public class UnitTest3 extends MainActivity{

    String password = "123456";
    String hashed = getSecurePassword(password);

    @Test
    public void hasher() {
        assertNotEquals(hashed, password);
    }

}
