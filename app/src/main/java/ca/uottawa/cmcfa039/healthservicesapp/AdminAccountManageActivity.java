package ca.uottawa.cmcfa039.healthservicesapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.ArrayList;
import com.google.firebase.FirebaseApp;
import java.io.*;


public class AdminAccountManageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    private ListView listView;
    private ArrayList<String> arrayListString = new ArrayList<>();
    private ArrayList<String> arrayListKey = new ArrayList<>();
    private ArrayList<User> arrayListUser = new ArrayList<>();
    private String currentSelected = null;
    private User currentUser = null;
    private ArrayAdapter<String> arrayAdapter;
    private Button backButton;
    private Button deleteButton;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_manage);

        mAuth = FirebaseAuth.getInstance();

        inializeUI();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAccountManageActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("/users/employees");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayListString);
        listView.setAdapter(arrayAdapter);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String employee = "Employee: " +dataSnapshot.getValue(Employee.class).getFirstName();
                employee += " " + dataSnapshot.getValue(Employee.class).getLastName();
                employee += " " + dataSnapshot.getValue(Employee.class).getEmail();

                String key = dataSnapshot.getKey();

                arrayListString.add(employee);
                arrayListKey.add(key);
                arrayListUser.add(dataSnapshot.getValue(User.class));
                arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String employee = "Employee: " +dataSnapshot.getValue(Employee.class).getFirstName();
                employee += " " + dataSnapshot.getValue(Employee.class).getLastName();
                employee += " " + dataSnapshot.getValue(Employee.class).getEmail();

                String key = dataSnapshot.getKey();

                arrayListString.remove(employee);
                arrayListKey.remove(key);
                arrayListUser.remove(dataSnapshot.getValue(User.class));
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("/users/patients");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String patient = "Patient: " + dataSnapshot.getValue(Patient.class).getFirstName();
                patient += " " + dataSnapshot.getValue(Patient.class).getLastName();
                patient += " " + dataSnapshot.getValue(Patient.class).getEmail();

                String key = dataSnapshot.getKey();

                arrayListString.add(patient);
                arrayListKey.add(key);
                arrayListUser.add(dataSnapshot.getValue(User.class));
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String patient = "Patient: " + dataSnapshot.getValue(Patient.class).getFirstName();
                patient += " " + dataSnapshot.getValue(Patient.class).getLastName();
                patient += " " + dataSnapshot.getValue(Patient.class).getEmail();

                String key = dataSnapshot.getKey();

                arrayListString.remove(patient);
                arrayListKey.remove(key);
                arrayListUser.remove(dataSnapshot.getValue(User.class));
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSelected = arrayListKey.get(position);
                currentUser = arrayListUser.get(position);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSelected == null){
                    Toast.makeText(getApplicationContext(), "Please select a user before deleting.", Toast.LENGTH_LONG).show();
                    return;
                }
                else {

                    String email = currentUser.getEmail();
                    String password = currentUser.getPassword();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(AdminAccountManageActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                deletion();
                            }
                        }
                    });

                }
            }
        });

    }

    public void deletion(){

        mDatabase.child("/users/employees/").child(currentSelected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDatabase.child("/users/employees").child(currentSelected).removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("/users/patients").child(currentSelected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDatabase.child("/users/patients").child(currentSelected).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAuth.getInstance().getCurrentUser().delete();
        mAuth = FirebaseAuth.getInstance();

        String secureP = getSecurePassword("5T5ptQ");

        mAuth.signInWithEmailAndPassword("admin@admin.ca", secureP);

        Toast.makeText(getApplicationContext(), "User deleted.", Toast.LENGTH_LONG).show();

    }

    public void inializeUI() {
        listView = findViewById(R.id.adminList);
        backButton = findViewById(R.id.backBtn);
        deleteButton = findViewById(R.id.deleteBtn);

    }

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




}
