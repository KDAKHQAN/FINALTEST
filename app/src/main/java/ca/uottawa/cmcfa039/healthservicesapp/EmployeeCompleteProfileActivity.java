package ca.uottawa.cmcfa039.healthservicesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeCompleteProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    private CheckBox ohipBox;
    private CheckBox insuranceBox;
    private CheckBox paymentBox;

    private EditText addressText;
    private EditText phoneText;
    private EditText clinicText;

    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_complete_profile);

        initializeUI();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = mAuth.getCurrentUser();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });


    }

    public void submit(){
        final String address = addressText.getText().toString();
        final String phoneNumber = phoneText.getText().toString();
        final String clinicName = clinicText.getText().toString();
        final Boolean ohip = ohipBox.isChecked();
        final Boolean insurance = insuranceBox.isChecked();
        final Boolean payment = paymentBox.isChecked();

        ArrayList<String> tempPaymentTypes = new ArrayList<>();

        if (TextUtils.isEmpty(address)) {
            Toast.makeText(getApplicationContext(), "Please enter an Address!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(getApplicationContext(), "Please enter a Phone Number!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(clinicName)) {
            Toast.makeText(getApplicationContext(), "Please enter a Clinic Name!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!ohip && !insurance && !payment){
            Toast.makeText(getApplicationContext(), "You must select at least one payment type!", Toast.LENGTH_LONG).show();
            return;
        }

        if(phoneNumber.length() != 10){
            Toast.makeText(getApplicationContext(), "Phone number either too long or too short!", Toast.LENGTH_LONG).show();
            return;
        }

        if(ohip){
            tempPaymentTypes.add("OHIP");
        }
        if(insurance){
            tempPaymentTypes.add("Private Insurance");
        }
        if(payment){
            tempPaymentTypes.add("Cash / Debit Cards / Credit Cards");
        }

        final ArrayList<String> finalPaymentTypes = tempPaymentTypes;

        mDatabase.child("/users/employees").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final String fName = dataSnapshot.getValue(Employee.class).getFirstName();
                final String lName = dataSnapshot.getValue(Employee.class).getLastName();
                final String password = dataSnapshot.getValue(Employee.class).getPassword();
                final String email = dataSnapshot.getValue(Employee.class).getEmail();

                final Employee mEmployee = new Employee(fName, lName, password, email,
                        finalPaymentTypes, address, clinicName, phoneNumber);

                mDatabase.child("/users/employees").child(mAuth.getCurrentUser().getUid()).setValue(mEmployee);

                Intent intent = new Intent(EmployeeCompleteProfileActivity.this, EmployeeSetHours.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void initializeUI(){
        ohipBox = findViewById(R.id.ohipCheckBox);
        insuranceBox = findViewById(R.id.insuranceCheckBox);
        paymentBox = findViewById(R.id.cashCheckBox);
        addressText = findViewById(R.id.addressText);
        phoneText = findViewById(R.id.phoneNumText);
        clinicText = findViewById(R.id.clinicNameText);
        submitButton = findViewById(R.id.submitButton);
    }

}
