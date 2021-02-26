package com.example.drag.iotmqtt;

public class UserHelperClass {

        int thour,tmin;
        int pakanGram;

    public UserHelperClass() {
    }

    public UserHelperClass(int thour, int tmin, int pakanGram) {
        this.thour = thour;
        this.tmin = tmin;
        this.pakanGram = pakanGram;
    }

    public int getThour() {
        return thour;
    }

    public void setThour(int thour) {
        this.thour = thour;
    }

    public int getTmin() {
        return tmin;
    }

    public void setTmin(int tmin) {
        this.tmin = tmin;
    }

    public int getpakanGram() {
        return pakanGram;
    }

    public void setpakanGram(int pakanGram) {
        this.pakanGram = pakanGram;
    }
}
