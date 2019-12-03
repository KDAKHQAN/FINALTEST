package ca.uottawa.cmcfa039.healthservicesapp;

import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AdminActivity extends AppCompatActivity {


    private Button serviceButton;
    private Button accountButton;
    private Button signOutButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initalizeUI();

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminAccountManageActivity.class);
                startActivity(intent);
            }
        });

        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        });
    }


    public void initalizeUI() {
        serviceButton = findViewById(R.id.serviceBtn);
        accountButton = findViewById(R.id.accountBtn);
        signOutButton = findViewById(R.id.signoutBtn);
    }


}
