package ca.uottawa.cmcfa039.healthservicesapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SplashActivity extends AppCompatActivity {

    private TextView welcomeEditText;
    private TextView signInEditText;
    private Button signOutButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = mAuth.getCurrentUser();


        initalizeUI();

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (mUser == null) {
            finish();
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

        }


        else {
            patientSplash();
        }
    }

    public void patientSplash() {
        mDatabase.child("/users/patients").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Patient currentPatient = dataSnapshot.getValue(Patient.class);

                    String firstName = currentPatient.getFirstName();
                    String type = "Patient";

                    welcomeEditText.setText("Welcome " + firstName);
                    signInEditText.setText("You're logged-in as a: " + type);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /*public void employeeSplash() {

        mDatabase.child("/users/employees").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Employee currentEmployee = dataSnapshot.getValue(Employee.class);

                    String firstName = currentEmployee.getFirstName();
                    String type = "Employee";

                    welcomeEditText.setText("Welcome " + firstName);
                    signInEditText.setText("You're logged-in as a: " + type);

                    Intent intent = new Intent(SplashActivity.this,EmployeeActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    /*public void adminSplash() {
        mDatabase.child("/users/admins").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Intent intent = new Intent(SplashActivity.this, ServiceActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    public void initalizeUI(){
        welcomeEditText = findViewById(R.id.welcomeEditText);
        signInEditText = findViewById(R.id.signInText);
        signOutButton = findViewById(R.id.signOutBtn);
    }


}


