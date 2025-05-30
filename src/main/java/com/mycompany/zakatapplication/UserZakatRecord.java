package com.mycompany.zakatapplication;


import java.io.Serializable;

public class UserZakatRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private double zakatFitrah = 0.0;
    private double ZakatIncome = 0.0;        // Zakat on gold
    private double ZakatAgriculture = 0.0;  // Zakat on income

    // Getters and setters
    public double getZakatFitrah() {
        return zakatFitrah;
    }

    public void setZakatFitrah(double zakatFitrah) {
        if (zakatFitrah >= 0) {
            this.zakatFitrah = zakatFitrah;
        }
    }

    public double getZakatIncome() {
        return ZakatIncome;
    }

    public void setZakatIncome(double ZakatIncome) {
        if (ZakatIncome >= 0) {
            this.ZakatIncome = ZakatIncome;
        }
    }

    public double getZakatAgriculture() {
        return ZakatAgriculture;
    }

    public void setZakatAgriculture(double ZakatAgriculture) {
        if (ZakatAgriculture >= 0) {
            this.ZakatAgriculture = ZakatAgriculture;
        }
    }

    // Calculate total zakat amount
    public double getTotalZakat() {
        return zakatFitrah + ZakatIncome + ZakatAgriculture;
    }

    @Override
    public String toString() {
        return String.format("Zakat Record [Fitrah=%.2f, Emas=%.2f, Pendapatan=%.2f, Total=%.2f]",
                zakatFitrah, ZakatIncome, ZakatAgriculture, getTotalZakat());
    }
}
