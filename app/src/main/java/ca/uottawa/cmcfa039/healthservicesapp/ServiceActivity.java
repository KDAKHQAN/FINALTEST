package ca.uottawa.cmcfa039.healthservicesapp;


import android.content.Intent;
import android.os.TestLooperManager;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ServiceActivity extends AppCompatActivity{

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    TextView confirmationText;
    EditText editService, editService2;
    Button serviceDelete, serviceAdd, serviceEdit, backButton;

    ListView serviceListUI;
    private ArrayList<Service> serviceArrayList = new ArrayList<>();
    private ArrayList<String> serviceArrayListString = new ArrayList<>();
    private ArrayAdapter<String> adapterServiceList;

    private Service serviceSelected;
    private String serviceSelectedName;
    private String serviceSelectedWorker;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        initializeUI();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("/servicesNew");

        adapterServiceList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,serviceArrayListString);
        serviceListUI.setAdapter(adapterServiceList);

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    String name = postSnapShot.getValue(Service.class).getName();
                    String worker = postSnapShot.getValue(Service.class).getWorker();
                    Service service = new Service(name, worker);
                    serviceArrayList.add(service);
                    serviceArrayListString.add(service.toString());
                }
                adapterServiceList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        serviceListUI.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                serviceSelected = serviceArrayList.get(position);
                serviceSelectedName = serviceArrayList.get(position).getName();
                serviceSelectedWorker = serviceArrayList.get(position).getWorker();
                editService.setHint(serviceSelectedName);
                editService2.setHint(serviceSelectedWorker);
            }
        });

        serviceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createService(editService.getText().toString(), editService2.getText().toString());
            }

        });

        serviceDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceSelectedName, serviceSelectedWorker);
            }
        });

        serviceEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editService(editService.getText().toString(), editService2.getText().toString());
                adapterServiceList.notifyDataSetChanged();
            }

        });

    }


    public Service createService(String serviceName, String employee){
        if(TextUtils.isEmpty(serviceName) || TextUtils.isEmpty(employee)){
            Toast.makeText(getApplicationContext(), "Please enter a service and an employee.", Toast.LENGTH_LONG).show();
            clearText();
            return null;
        }

        if(employee.equals("Doctor") || employee.equals("Nurse") || employee.equals("Staff")) {
            final String serviceNameFinal = serviceName;
            final Service mService = new Service(serviceName, employee);
            mDatabaseRef.child("/servicesNew").child(serviceNameFinal).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        confirmationText.setText("Service already exists");
                        clearText();
                        return;
                    } else {
                        mDatabase.getReference("/servicesNew").child(serviceNameFinal).setValue(mService);
                        Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_LONG).show();
                        clearText();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter a valid employee (Doctor, Nurse, Staff)", Toast.LENGTH_LONG).show();
            clearText();
            return null;
        }
        Service newService = new Service(editService.getText().toString(), editService2.getText().toString());
        serviceArrayList.add(newService);
        serviceArrayListString.add(newService.toString());
        adapterServiceList.notifyDataSetChanged();
        resetHint();
        return newService;
    }

    public void deleteService(String serviceName, String employee){
        if(TextUtils.isEmpty(serviceName)|| TextUtils.isEmpty(employee)){
            Toast.makeText(getApplicationContext(), "Please select a service.", Toast.LENGTH_LONG).show();
            clearText();
            return;
        }
        mDatabase.getReference("/servicesNew").child(serviceName).removeValue();
        Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_LONG).show();
        clearText();
        serviceArrayList.remove(serviceSelected);
        serviceArrayListString.remove(serviceSelected.toString());
        adapterServiceList.notifyDataSetChanged();
        resetHint();
    }

    public void editService(String newS, String newE) {
        if (TextUtils.isEmpty(newS) || TextUtils.isEmpty(newE)){
            Toast.makeText(getApplicationContext(), "Please enter a service and a valid employee.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(serviceSelectedName) || TextUtils.isEmpty(serviceSelectedWorker)){
            Toast.makeText(getApplicationContext(), "Please select a service.", Toast.LENGTH_LONG).show();
            return;
        }
        Service isNull = createService(newS, newE);
        if(isNull == null){
            return;
        }
        deleteService(serviceSelectedName, serviceSelectedWorker);
        resetHint();
        serviceSelected = null;
        serviceSelectedName = null;
        serviceSelectedWorker = null;
    }

    public void clearText(){
        editService.setText("");
        editService2.setText("");
    }

    public void initializeUI(){
        confirmationText = findViewById(R.id.confirmationText);
        editService = findViewById(R.id.editService);
        editService2 = findViewById(R.id.editService2);
        serviceDelete = findViewById(R.id.serviceDelete);
        serviceAdd = findViewById(R.id.serviceAdd);
        serviceEdit = findViewById(R.id.serviceEdit);
        backButton = findViewById(R.id.backBtn);
        serviceListUI = findViewById(R.id.serviceListUI);
    }

    public void resetHint(){
        editService.setHint("Enter Service");
        editService2.setHint("Who Performs this Service");
    }

}