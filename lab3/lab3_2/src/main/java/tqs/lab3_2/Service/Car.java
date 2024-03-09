package tqs.lab3_2.Service;


public class Car{

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

    }

    public int hashCode(){

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
        // TODO Auto-generated method stub
        return super.toString();
    }

}