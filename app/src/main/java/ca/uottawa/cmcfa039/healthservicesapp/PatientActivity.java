package ca.uottawa.cmcfa039.healthservicesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ServiceConfigurationError;

public class PatientActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private ListView clinicList;
    private TextView patientText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    private EditText searchBar;
    private ListView serviceList;
    private ListView appointmentList;
    private Button signOutBtn;

    private ArrayAdapter<String> adapter;
    private ArrayList<Employee> employeeArrayList = new ArrayList<>();
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private ArrayList<Float> beforeFloats = new ArrayList<>();
    private ArrayList<String> idArrayList = new ArrayList<>();
    private Employee selectedEmployee;
    private String selectedEmployeeID;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Employee mEmployee;
    private Patient mPatient;
    private Service mService;
    private String date;
    private Appointment appt;

    private ArrayList<Service> employeeServiceArrayList = new ArrayList<>();
    private ArrayList<String> employeeServiceArrayListString = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterEmployeeList;
    private ListView apptListView;


    private ArrayList<String> apptArrayList = new ArrayList<>();
    private ArrayList<Appointment> apptRealList = new ArrayList<>();
    private ArrayAdapter<String> apptAdapter;
    private Appointment selectedAppt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        initalizeUI();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PatientActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stringArrayList);
        clinicList.setAdapter(adapter);
        mDatabase = FirebaseDatabase.getInstance().getReference("/users/employees");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String employeeString = dataSnapshot.getValue(Employee.class).toString();
                Employee mEmployee = dataSnapshot.getValue(Employee.class);
                String id = dataSnapshot.getKey();
                String clinicNameCheck = dataSnapshot.getValue(Employee.class).getClinicName();
                ArrayList<Float> floats = dataSnapshot.getValue(Employee.class).getRatingArray();
                Float value = 0f;

                for(int i = 0; i < floats.size(); i ++){
                    value += floats.get(i);
                }

                Float v = value / floats.size();

                String valueString = "\nRating: " + v;
                employeeString += valueString;

                if(clinicNameCheck != null) {
                    stringArrayList.add(employeeString);
                    employeeArrayList.add(mEmployee);
                    idArrayList.add(id);
                    beforeFloats.add(v);
                }

            adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String employeeString = dataSnapshot.getValue(Employee.class).toString();
                Employee mEmployee = dataSnapshot.getValue(Employee.class);
                String id = dataSnapshot.getKey();
                String clinicNameCheck = dataSnapshot.getValue(Employee.class).getClinicName();

                if(clinicNameCheck != null) {
                    stringArrayList.remove(employeeString);
                    employeeArrayList.remove(mEmployee);
                    idArrayList.remove(id);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        apptAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,apptArrayList);
        apptListView.setAdapter(apptAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference("/appointments").child(mUser.getUid());
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Appointment clinic = dataSnapshot.getValue(Appointment.class);

                apptRealList.add(clinic);
                apptArrayList.add(clinic.toString());
                apptAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String clinic = dataSnapshot.getValue(Appointment.class).getAssignedEmployee().getClinicName();
                String service = dataSnapshot.getValue(Appointment.class).getAppointmentService().toString();
                String tempDate = dataSnapshot.getValue(Appointment.class).getAppointmentDate();

                String apptFinal = (clinic + "\n" + service + "\n" + tempDate + "\n");
                apptArrayList.remove(apptFinal);

                apptAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clinicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedEmployee = employeeArrayList.get(position);
                selectedEmployeeID = idArrayList.get(position);
                showPopup(view);
            }
        });

        apptListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAppt = apptRealList.get(position);
                apptPopup(view);
            }
        });


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (PatientActivity.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.patient_popup);
        popupMenu.show();

    }

    public void apptPopup(View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.appt_popup);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month + 1;
                date = month + "/" + day + "/" + year;

            }
        };

        switch (item.getItemId()){
            case R.id.item1:

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(PatientActivity.this);
                builderSingle.setTitle("Select One");

                final ArrayAdapter<String> arrayAdapterEmployeeList = new ArrayAdapter<String>(PatientActivity.this, android.R.layout.select_dialog_singlechoice, employeeServiceArrayListString);
                mDatabase = FirebaseDatabase.getInstance().getReference("users/employees").child(selectedEmployeeID).child("services");
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

                builderSingle.setAdapter(arrayAdapterEmployeeList, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapterEmployeeList.getItem(which);
                        mService = employeeServiceArrayList.get(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(PatientActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Service is");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                appt = new Appointment(mEmployee, mPatient, mService, date);
                                mDatabase = FirebaseDatabase.getInstance().getReference("/appointments").child(mUser.getUid()).child(mService.getName() + " " + mEmployee.getClinicName());
                                mDatabase.setValue(appt);
                                arrayAdapterEmployeeList.clear();
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Appointment Created.", Toast.LENGTH_LONG).show();
                            }
                        });
                        builderInner.show();
                    }
                });
                builderSingle.show();
                mDatabase = FirebaseDatabase.getInstance().getReference("users/employees").child(selectedEmployeeID);
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mEmployee = dataSnapshot.getValue(Employee.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                mDatabase = FirebaseDatabase.getInstance().getReference("users/patients").child(mUser.getUid());
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mPatient = dataSnapshot.getValue(Patient.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(PatientActivity.this,
                        android.R.style.Theme, mDateSetListener,year, month, day);
                dialog.show();

                return true;
        }


        switch (item.getItemId()){
            case R.id.item2:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PatientActivity.this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.activity_rate_clinic, null);
                dialogBuilder.setView(dialogView);

                final EditText commentText = dialogView.findViewById(R.id.commentText);
                final RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);

                dialogBuilder.setTitle("Rate Clinic");
                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ratingBar.setNumStars(5);
                        final String comment = commentText.getText().toString();
                        final Float rating = ratingBar.getRating();

                        Toast.makeText(getApplicationContext(), "Thank you for your rating.", Toast.LENGTH_LONG).show();
                        mDatabase = FirebaseDatabase.getInstance().getReference("/users/employees").child(selectedEmployeeID).child("ratingArray");

                        final ArrayList<Float> tempArray = new ArrayList<>();
                        tempArray.add(rating);

                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()){
                                    Float value = postSnapShot.getValue(Float.class);
                                    tempArray.add(value);
                                }
                                mDatabase = FirebaseDatabase.getInstance().getReference("/users/employees").child(selectedEmployeeID);
                                selectedEmployee.setRatingArray(tempArray);
                                mDatabase.setValue(selectedEmployee);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        mDatabase = FirebaseDatabase.getInstance().getReference("/comments").child(selectedEmployeeID).child("comments");
                        DatabaseReference ref = mDatabase.push();
                        if(!comment.equals("")){
                            ref.setValue(comment);
                        }

                    }
                });
                dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
        }

        switch (item.getItemId()){
            case R.id.item3:
                mDatabase = FirebaseDatabase.getInstance().getReference("/appointments").child(mUser.getUid()).child(selectedAppt.getAppointmentService().getName() + " " + selectedAppt.getAssignedEmployee().getClinicName());
                apptRealList.remove(selectedAppt);
                mDatabase.removeValue();
                apptAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Checked-in.", Toast.LENGTH_LONG).show();
        }


        switch (item.getItemId()){
            case R.id.item4:
                mDatabase = FirebaseDatabase.getInstance().getReference("/appointments").child(mUser.getUid()).child(selectedAppt.getAppointmentService().getName() + " " + selectedAppt.getAssignedEmployee().getClinicName());
                apptRealList.remove(selectedAppt);
                mDatabase.removeValue();
                apptAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Appointment Cancelled.", Toast.LENGTH_LONG).show();
        }


        return false;
    }

    public void initalizeUI(){
        clinicList = findViewById(R.id.clinicList);
        patientText = findViewById(R.id.welcomePatientText);
        searchBar = findViewById(R.id.searchBar);
        apptListView = findViewById(R.id.apptList);
        signOutBtn = findViewById(R.id.patientSignOutBtn);
    }
}
