package com.example.drag.iotmqtt;

public class alarm {
        private String jam;
    private String menit;
    private String detik;
    private String jumlahpakan;

    public alarm() {
    }

    public alarm(String jam, String menit, String detik, String jumlahpakan) {
        this.jam = jam;
        this.menit = menit;
        this.detik = detik;
        this.jumlahpakan = jumlahpakan;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getMenit() {
        return menit;
    }

    public void setMenit(String menit) {
        this.menit = menit;
    }

    public String getDetik() {
        return detik;
    }

    public void setDetik(String detik) {
        this.detik = detik;
    }

    public String getJumlahpakan() {
        return jumlahpakan;
    }

    public void setJumlahpakan(String jumlahpakan) {
        this.jumlahpakan = jumlahpakan;
    }
}
