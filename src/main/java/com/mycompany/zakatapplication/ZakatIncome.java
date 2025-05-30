package com.mycompany.zakatapplication;

public class ZakatIncome extends ZakatCalculation {
    private double income;
    private double expenses;

    public ZakatIncome(double income, double expenses) {
        this.income = income;
        this.expenses = expenses;
    }

    @Override
    public double calculateZakat() {
        double netIncome = income - expenses;
        if (netIncome >= 4000) {
            return netIncome * 0.025;
        } else {
            return 0.0;
        }
    }
}
