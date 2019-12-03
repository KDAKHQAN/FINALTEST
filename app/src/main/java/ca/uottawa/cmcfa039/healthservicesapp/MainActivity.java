package ca.uottawa.cmcfa039.healthservicesapp;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button createAcctBtn;
    private Button loginBtn;


    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();


        initalizeUI();

       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               loginUserAccount();
           }
       });

        createAcctBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              goToSignUp();
          }
        });
    }



    public void goToSignUp() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void loginUserAccount() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        password = getSecurePassword(password);

        login(email, password);


    }
    public void login (String email, String password) {
        if(email.equals("admin") || email.equals("admin@admin.ca")){

            email = "admin@admin.ca";

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {

                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        else {
            mRef = FirebaseDatabase.getInstance().getReference();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        mUser = mAuth.getCurrentUser();
                        mRef.child("/users/employees").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    Intent intent = new Intent(MainActivity.this,EmployeeActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent = new Intent(MainActivity.this, PatientActivity.class);
                                    startActivity(intent);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    //hashing passwords
    public String getSecurePassword(String password) {

        StringBuilder sbuild = new StringBuilder();


        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            for (byte i : hash) {
                sbuild.append(String.format("%02x", i));
            }
        } catch (NoSuchAlgorithmException e) {

        }

        return sbuild.toString();
    }


    public void initalizeUI() {
        emailEditText = findViewById(R.id.emText);
        passwordEditText = findViewById(R.id.pwText);
        createAcctBtn = findViewById(R.id.createAccountBtn);
        loginBtn = findViewById(R.id.loginBtn);
    }
}
