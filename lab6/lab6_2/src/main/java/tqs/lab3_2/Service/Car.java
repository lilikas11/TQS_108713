package tqs.lab3_2.Service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Car{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long carld;

    private String maker;
    private String model;

    public Car(){

    }

    public Car(String maker, String model){
        this.maker = maker;
        this.model = model;
    }

    public boolean equals(Object car){
        return super.equals(car);

    }

    public int hashCode(){
        return super.hashCode();

    }

    public long getCarld() {
        return carld;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public void setCarld(long carld) {
        this.carld = carld;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car "+ carld +", Maker: "+ maker +", Model: "+this.model+";";
    }

}