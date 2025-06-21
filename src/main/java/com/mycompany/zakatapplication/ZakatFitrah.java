package com.mycompany.zakatapplication;

public class ZakatFitrah extends ZakatCalculation {
    private int totalPeople;
    private String state;
    private String riceType;

    // States and their respective values
    private static final String[] STATES = { "Kedah", "Selangor", "Negeri Sembilan" };
    private static final String[] RICE_TYPES = { "Economical Rice", "Regular Rice", "Premium Rice" };

    // Map to store rice prices for each state and rice type
    private static final double[][] RICE_PRICES = {
            { 7.00, 14.00, 14.00}, // Kedah
            { 7.00, 15.00, 22.00 }, // Selangor
            { 7.50, 12.00, 22.00 }  // Negeri Sembilan
    };

    public ZakatFitrah(int totalPeople, String state, String riceType) {
        this.totalPeople = totalPeople;
        this.state = state;
        this.riceType = riceType;
    }

    @Override
    public double calculateZakat() {
        // Ensure totalPeople is not negative
        if (totalPeople < 0) {
            return 0.0;
        }

        // Validate state and rice type
        if (!isValidState(state)) {
            System.out.println("Invalid state selected.");
            return 0.0;
        }

        if (!isValidRiceType(riceType)) {
            System.out.println("Invalid rice type selected.");
            return 0.0;
        }

        // Get the index of the selected state
        int stateIndex = getStateIndex(state);

        // Get the index of the selected rice type
        int riceIndex = getRiceTypeIndex(riceType);

        // Retrieve the price of the selected rice type for the selected state
        double ricePrice = RICE_PRICES[stateIndex][riceIndex];

        // Calculate zakat
        return totalPeople * ricePrice;
    }

    private boolean isValidState(String state) {
        for (String s : STATES) {
            if (s.equalsIgnoreCase(state)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidRiceType(String riceType) {
        for (String rt : RICE_TYPES) {
            if (rt.equalsIgnoreCase(riceType)) {
                return true;
            }
        }
        return false;
    }

    private int getStateIndex(String state) {
        for (int i = 0; i < STATES.length; i++) {
            if (STATES[i].equalsIgnoreCase(state)) {
                return i;
            }
        }
        return -1; // Default value if state is invalid
    }

    private int getRiceTypeIndex(String riceType) {
        for (int i = 0; i < RICE_TYPES.length; i++) {
            if (RICE_TYPES[i].equalsIgnoreCase(riceType)) {
                return i;
            }
        }
        return -1; // Default value if rice type is invalid
    }
}