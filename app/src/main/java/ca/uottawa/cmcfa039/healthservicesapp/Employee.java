
package ca.uottawa.cmcfa039.healthservicesapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Employee extends Client {

	private ArrayList<Service> services;
	private ArrayList<String> paymentTypes;
	private String address;
	private String clinicName;
	private String phoneNumber;
	private ArrayList<String> timeArray;
	private ArrayList<Float> ratingArray;

	public Employee (){
		super ();
		services = new ArrayList<>();
		paymentTypes = new ArrayList<>();
		address = null;
		clinicName = null;
		phoneNumber = null;
		timeArray = new ArrayList<>();
		ratingArray = new ArrayList<>();

	}
	public Employee (String fName, String lName, String password, String em){
		super (fName, lName, password, em);
		services = new ArrayList<>();
		paymentTypes = new ArrayList<>();
		address = null;
		clinicName = null;
		phoneNumber = null;
		timeArray = new ArrayList<>();
		ratingArray = new ArrayList<>();
	}

	public Employee (String fName, String lName, String password, String em, ArrayList<String> pt,
					 String add, String cName, String pNum){
		super (fName, lName, password, em);
		services = new ArrayList<>();
		paymentTypes = pt;
		address = add;
		clinicName = cName;
		phoneNumber = pNum;
		timeArray = new ArrayList<>();
		ratingArray = new ArrayList<>();
	}

	public String getAddress() {
		return address;
	}

	public String getClinicName() {
		return clinicName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public ArrayList<String> getPaymentTypes() {
		return paymentTypes;
	}

	public ArrayList<String> getTimeArray() {
		return timeArray;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

	public void setPaymentTypes(ArrayList<String> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public void setTimeArray(ArrayList<String> timeArray) {
		this.timeArray = timeArray;
	}

	public ArrayList<Float> getRatingArray() {
		return ratingArray;
	}

	public void setRatingArray(ArrayList<Float> ratingArray) {
		this.ratingArray = ratingArray;
	}

	@NonNull
	@Override
	public String toString() {
		return (clinicName + "\n" + address + "\n" + phoneNumber);
	}
}
