package com.example.l08t2;

public class Bottle {

    private String name = "Pepsi Max";
    private String manufacturer = "Pepsi";
    private double total_energy = 0.33;
    private double price = 1.80;
    private double size = 0.5;
    private int amount;

    public Bottle(){}

    public Bottle(String n, String manuf, double totE, double p, double s, int amnt){
        name = n;
        manufacturer = manuf;
        total_energy = totE;
        price = p;
        size = s;
        amount = amnt;
    }
    @Override
    public String toString(){
        return name + " " + size;
    }
    public String getName(){
        return name;
    }
    public String getManufacturer(){
        return manufacturer;
    }
    public double getEnergy(){
        return total_energy;
    }
    public double getPrice(){
        return price;
    }
    public double getSize(){
        return size;
    }
    public int getAmount(){return amount;}
    public void setAmount(int amnt) {
        amount = amnt;
    }
}
