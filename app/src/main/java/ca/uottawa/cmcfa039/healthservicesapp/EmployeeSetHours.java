package ca.uottawa.cmcfa039.healthservicesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeSetHours extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    private Button backButton;
    private EditText monStart;
    private EditText monEnd;
    private EditText tuesStart;
    private EditText tuesEnd;
    private EditText wedStart;
    private EditText wedEnd;
    private EditText thuStart;
    private EditText thuEnd;
    private EditText friStart;
    private EditText friEnd;
    private EditText satStart;
    private EditText satEnd;
    private EditText sunStart;
    private EditText sunEnd;

    private TimePickerDialog timePickerDialog;
    private String amPm;

    private int currentHour;
    private int currentMinute;

    private ArrayList<String> timeArray = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_set_hours);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initalizeUI();

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


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = FirebaseDatabase.getInstance().getReference("/users/employees").child(mUser.getUid()).child("timeArray");
                mDatabase.removeValue();
                mDatabase.setValue(timeArray);
                Intent intent = new Intent(EmployeeSetHours.this, EmployeeActivity.class);
                startActivity(intent);

            }
        });


        monStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        monStart.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(0, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });

        monEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        monEnd.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(1, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });

        tuesStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        tuesStart.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(2, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        tuesEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        tuesEnd.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(3, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        wedStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        wedStart.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(4, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        wedEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        wedEnd.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(5, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        thuStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        thuStart.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(6, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        thuEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        thuEnd.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(7, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        friStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        friStart.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(8, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        friEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        friEnd.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(9, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        satStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        satStart.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(10, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        satEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        satEnd.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(11, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        sunStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        sunStart.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(12, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
        sunEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(EmployeeSetHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12){
                            amPm = "PM";
                        }
                        else {
                            amPm = "AM";
                        }

                        sunEnd.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                        timeArray.set(13, String.format("%02d:%02d", hourOfDay, minute) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });


    }

    public void initalizeUI(){

        backButton = findViewById(R.id.applyButton);
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

        monStart.setShowSoftInputOnFocus(false);
        monEnd.setShowSoftInputOnFocus(false);
        tuesStart.setShowSoftInputOnFocus(false);
        tuesEnd.setShowSoftInputOnFocus(false);
        wedStart.setShowSoftInputOnFocus(false);
        wedEnd.setShowSoftInputOnFocus(false);
        thuStart.setShowSoftInputOnFocus(false);
        thuEnd.setShowSoftInputOnFocus(false);
        friStart.setShowSoftInputOnFocus(false);
        friEnd.setShowSoftInputOnFocus(false);
        satStart.setShowSoftInputOnFocus(false);
        satEnd.setShowSoftInputOnFocus(false);
        sunStart.setShowSoftInputOnFocus(false);
        sunEnd.setShowSoftInputOnFocus(false);



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
