package com.mycompany.zakatapplication;

public class ZakatFitrah extends ZakatCalculation {
    private int totalPeople;
    private static final double RICE_PRICE = 1.83;
    private static final double FITRAH_PER_PERSON_KG = 2.5;

    public ZakatFitrah(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    @Override
    public double calculateZakat() {
        // Ensure totalPeople is not negative
        if (totalPeople < 0) {
            return 0.0;
        }
        return totalPeople * RICE_PRICE * FITRAH_PER_PERSON_KG;
    }
}