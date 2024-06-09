package com.nhom10.pbl.payload.request;

import lombok.Data;

@Data
public class PatientUpdateRequest {
    private String nhommau;
    private double cannang;
    private double chieucao;
    private String benhnen;

    // Getters and Setters
    public String getNhommau() {
        return nhommau;
    }

    public void setNhommau(String nhommau) {
        this.nhommau = nhommau;
    }

    public double getCannang() {
        return cannang;
    }

    public void setCannang(double cannang) {
        this.cannang = cannang;
    }

    public double getChieucao() {
        return chieucao;
    }

    public void setChieucao(double chieucao) {
        this.chieucao = chieucao;
    }

    public String getBenhnen() {
        return benhnen;
    }

    public void setBenhnen(String benhnen) {
        this.benhnen = benhnen;
    }
}
