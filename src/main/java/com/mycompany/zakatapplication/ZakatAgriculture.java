package com.mycompany.zakatapplication;

public class ZakatAgriculture extends ZakatCalculation {
    private double harvestKg;
    private double rate;
    private static final double WHEAT_PRICE = 1.30;

    public ZakatAgriculture(double harvestKg, double rate) {
        this.harvestKg = harvestKg;
        this.rate = rate;
    }

    @Override
    public double calculateZakat() {
        double zakatKg = harvestKg * rate;
        return zakatKg * WHEAT_PRICE;
    }
}
