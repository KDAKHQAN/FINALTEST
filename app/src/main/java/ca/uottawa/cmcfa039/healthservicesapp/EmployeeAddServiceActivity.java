package ca.uottawa.cmcfa039.healthservicesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Collections;

public class EmployeeAddServiceActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    private Button backButton;
    private Button addButton;
    private Button deleteButton;

    private ListView allServiceList;
    private ListView employeeServiceList;

    private ArrayList<Service> allServiceArrayList = new ArrayList<>();
    private ArrayList<String> allServiceArrayListString = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterServiceList;


    private ArrayList<Service> employeeServiceArrayList = new ArrayList<>();
    private ArrayList<String> employeeServiceArrayListString = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterEmployeeList;

    private Service allServiceSelected;
    private String allServiceSelectedName;
    private String allServiceSelectedWorker;
    private Service employeeServiceSelected;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add_service);

        initalizeUI();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference("/servicesNew");
        arrayAdapterServiceList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,allServiceArrayListString);
        allServiceList.setAdapter(arrayAdapterServiceList);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    String name = postSnapShot.getValue(Service.class).getName();
                    String worker = postSnapShot.getValue(Service.class).getWorker();
                    Service service = new Service(name, worker);
                    allServiceArrayList.add(service);
                    allServiceArrayListString.add(service.toString());
                }
                arrayAdapterServiceList.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        arrayAdapterEmployeeList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,employeeServiceArrayListString);
        employeeServiceList.setAdapter(arrayAdapterEmployeeList);
        mDatabase = FirebaseDatabase.getInstance().getReference("users/employees").child(mUser.getUid()).child("services");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    String name = postSnapShot.getValue(Service.class).getName();
                    String worker = postSnapShot.getValue(Service.class).getWorker();
                    Service service = new Service(name, worker);
                    employeeServiceArrayList.add(service);
                    employeeServiceArrayListString.add(service.toString());
                }
                arrayAdapterEmployeeList.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference("/users/employees").child(mUser.getUid()).child("services");
                mDatabase.removeValue();
                mDatabase.setValue(employeeServiceArrayList);
                Intent intent = new Intent(EmployeeAddServiceActivity.this, EmployeeActivity.class);
                startActivity(intent);
            }
        });

        allServiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                allServiceSelectedName = allServiceArrayList.get(position).getName();
                allServiceSelectedWorker = allServiceArrayList.get(position).getWorker();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Service checker = new Service(allServiceSelectedName, allServiceSelectedWorker);

                if(allServiceSelectedName == null && allServiceSelectedWorker == null){
                    Toast.makeText(getApplicationContext(), "Please select a service from the available services list.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(employeeServiceArrayListString.contains(checker.toString())){
                    Toast.makeText(getApplicationContext(), "Already have that service.", Toast.LENGTH_LONG).show();
                    return;
                }

                else {
                    employeeServiceArrayList.add(checker);
                    employeeServiceArrayListString.add(checker.toString());
                    arrayAdapterEmployeeList.notifyDataSetChanged();
                }
            }
        });

        employeeServiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                employeeServiceSelected = employeeServiceArrayList.get(position);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(employeeServiceSelected == null){
                    Toast.makeText(getApplicationContext(), "Please select a service from your service list.", Toast.LENGTH_LONG).show();
                    return;
                }

                else if (!employeeServiceArrayList.contains(employeeServiceSelected)) {
                    Toast.makeText(getApplicationContext(), "Your services does not contain that service, please select a different service.", Toast.LENGTH_LONG).show();
                    return;
                }

                else {
                    employeeServiceArrayList.remove(employeeServiceSelected);
                    employeeServiceArrayListString.remove(employeeServiceSelected.toString());
                    arrayAdapterEmployeeList.notifyDataSetChanged();
                }

            }
        });


    }


    public void initalizeUI(){
        backButton = findViewById(R.id.backBtn);
        deleteButton = findViewById(R.id.deleteBtn);
        addButton = findViewById(R.id.addBtn);
        allServiceList = findViewById(R.id.allServicesListView);
        employeeServiceList = findViewById(R.id.employeeServicesListView);


    }
}
