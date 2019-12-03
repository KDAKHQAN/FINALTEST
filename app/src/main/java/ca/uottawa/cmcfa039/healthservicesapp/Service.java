package ca.uottawa.cmcfa039.healthservicesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Service {

    private String name;
    private String worker;

    Service() {}

    Service(String n, String w){
        this.name = n;
        this.worker = w;
    }

    public String getName() {
        return name;
    }

    public String getWorker() {
        return worker;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    @NonNull
    @Override
    public String toString() {
        return ("Service: " + name + "\n" + "Employee: " + worker);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == this){
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }

        Service service = (Service) obj;
        return name == service.name && worker == service.worker;
    }
}