package ca.uottawa.cmcfa039.healthservicesapp;

public class Review {

    Employee clinic;
    Patient reviewer;
    int rating;
    String comment;

    public Review(){
        clinic = null;
        reviewer = null;
        rating = 0;
        comment = "null";

    }

    public Review(Employee e, Patient p, int r, String c){
        clinic = e;
        reviewer = p;
        rating = r;
        comment = c;
    }

    public Employee getClinic() {
        return clinic;
    }

    public Patient getReviewer() {
        return reviewer;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setClinic(Employee clinic) {
        this.clinic = clinic;
    }

    public void setReviewer(Patient reviewer) {
        this.reviewer = reviewer;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
