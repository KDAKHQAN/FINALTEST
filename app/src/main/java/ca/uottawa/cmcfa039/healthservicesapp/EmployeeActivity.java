package ca.uottawa.cmcfa039.healthservicesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {

    private TextView welcomeText;
    private TextView addressText;
    private TextView phoneNumText;
    private ListView serviceList;
    private Button addButton;
    private Button signOutButton;
    private Button setHoursButton;

    private TextView monStart;
    private TextView monEnd;
    private TextView tuesStart;
    private TextView tuesEnd;
    private TextView wedStart;
    private TextView wedEnd;
    private TextView thuStart;
    private TextView thuEnd;
    private TextView friStart;
    private TextView friEnd;
    private TextView satStart;
    private TextView satEnd;
    private TextView sunStart;
    private TextView sunEnd;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private ArrayList<Service> serviceArrayList = new ArrayList<>();
    private ArrayList<String> timeArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        initializeUI();


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = mAuth.getCurrentUser();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeActivity.this, EmployeeAddServiceActivity.class);
                startActivity(intent);
            }
        });

        setHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeActivity.this, EmployeeSetHours.class);
                startActivity(intent);
            }
        });


        mDatabase.child("/users/employees").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Employee.class).getAddress() == null
                        || dataSnapshot.getValue(Employee.class).getClinicName() == null
                        || dataSnapshot.getValue(Employee.class).getPaymentTypes() == null) {

                    Intent intent = new Intent(EmployeeActivity.this,EmployeeCompleteProfileActivity.class);
                    startActivity(intent);
                }

                else {
                    welcomeText.setText(dataSnapshot.getValue(Employee.class).toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stringArrayList);
        serviceList.setAdapter(arrayAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference("/users/employees").child(mUser.getUid()).child("services");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    String name = postSnapShot.getValue(Service.class).getName();
                    String worker = postSnapShot.getValue(Service.class).getWorker();
                    Service service = new Service(name, worker);
                    serviceArrayList.add(service);
                    stringArrayList.add(service.toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(EmployeeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        for(int i = 0; i < 14; i++){
            timeArray.add("00:00" + "AM");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("/users/employees").child(mUser.getUid()).child("timeArray");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    timeArray = (ArrayList<String>) dataSnapshot.getValue();
                }

                massSetText();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public void initializeUI(){
        welcomeText = findViewById(R.id.welcomeText);
        serviceList = findViewById(R.id.serviceList);
        addButton = findViewById(R.id.addServiceBtn);
        signOutButton = findViewById(R.id.signOutBtn);
        setHoursButton = findViewById(R.id.setHrsBtn);
        monStart = findViewById(R.id.monStart);
        monEnd = findViewById(R.id.monEnd);
        tuesStart = findViewById(R.id.tuesStart);
        tuesEnd = findViewById(R.id.tuesEnd);
        wedStart = findViewById(R.id.wedStart);
        wedEnd = findViewById(R.id.wedEnd);
        thuStart = findViewById(R.id.thuStart);
        thuEnd = findViewById(R.id.thuEnd);
        friStart = findViewById(R.id.friStart);
        friEnd = findViewById(R.id.friEnd);
        satStart = findViewById(R.id.satStart);
        satEnd = findViewById(R.id.satEnd);
        sunStart = findViewById(R.id.sunStart);
        sunEnd = findViewById(R.id.sunEnd);

    }

    public void massSetText(){
        monStart.setText(timeArray.get(0));
        monEnd.setText(timeArray.get(1));
        tuesStart.setText(timeArray.get(2));
        tuesEnd.setText(timeArray.get(3));
        wedStart.setText(timeArray.get(4));
        wedEnd.setText(timeArray.get(5));
        thuStart.setText(timeArray.get(6));
        thuEnd.setText(timeArray.get(7));
        friStart.setText(timeArray.get(8));
        friEnd.setText(timeArray.get(9));
        satStart.setText(timeArray.get(10));
        satEnd.setText(timeArray.get(11));
        sunStart.setText(timeArray.get(12));
        sunEnd.setText(timeArray.get(13));
    }


}

