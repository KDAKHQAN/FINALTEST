package ca.uottawa.cmcfa039.healthservicesapp;

import androidx.annotation.NonNull;

public class Appointment {
    Employee assignedEmployee;
    Patient appointmentPatient;
    String appointmentDate;
    Service appointmentService;

    public Appointment (Employee newEmployee, Patient newPatient,Service newService, String date){
        assignedEmployee = newEmployee;
        appointmentPatient = newPatient;
        appointmentService = newService;
        appointmentDate = date;
    }

    public Appointment() {
        assignedEmployee = null;
        appointmentPatient = null;
        appointmentService = null;
        appointmentDate = null;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public Service getAppointmentService() {
        return appointmentService;
    }

    public Patient getAppointmentPatient() {
        return appointmentPatient;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    @NonNull
    @Override
    public String toString() {
        return (assignedEmployee.getClinicName() + "\n" + appointmentService.toString() + "\n" + appointmentDate + "\n");
    }
}
