// Author:  Shehryar Rashid Mehmood
// Aire Logic Medical Score Calculation

import java.text.DecimalFormat;

public class Main {

    // ---------- AUXILIARY FUNCTIONS ----------

    // Function to round float to single decimal place
    private static float roundFloat(float num){
        DecimalFormat format = new DecimalFormat("#.#");
        return Float.parseFloat(format.format(num));
    }


    // ---------- RESPIRATION SCORE FUNCTIONS ----------

    // Function to get the score for respiration range
    private static int getRespirationScore(int respirationRange) {
        if (respirationRange <= 8 || respirationRange >= 25) {
            return 3;
        } else if (respirationRange >= 21) {
            return 2;
        } else if (respirationRange <= 11) {
            return 1;
        }
        return 0;
    }


    // ---------- TEMPERATURE SCORE FUNCTIONS ----------

    // Function to get the score for temperature range
    private static int getTemperatureScore(float temperature) {
        if (temperature <= 35.0) {
            return 3;
        } else if (temperature >= 39.1) {
            return 2;
        } else if ((temperature >= 38.1 && temperature <= 39.0) || (temperature >= 35.1 && temperature <= 36.0)) {
            return 1;
        }
        return 0;
    }


    // ------------ CALCULATING SPO2 SCORE ----------

    // Auxiliary Function to get SP02 for Air Patients
    private static int airSPO2(int spO2){
        if (spO2 <= 83){
            return 3;
        }
        else if (spO2 <= 85){
            return 2;
        }
        else if (spO2 <= 87) {
            return 1;
        }
        return 0;
    }

    // Auxiliary Function to get SP02 for Oxygen Patients
    private static int o2SPO2(int spO2){
        if (spO2 <= 83 || spO2 >= 97){
            return 3;
        }
        else if (spO2 <= 85 || spO2 >= 95){
            return 2;
        }
        else if (spO2 <= 87 || spO2 >= 93){
            return 1;
        }
        return 0;
    }

    // Function to get spO2 score
    private static int getSPO2(AirOxygen airOxygen, int spO2){

        if (airOxygen == AirOxygen.OXYGEN)
            return o2SPO2(spO2);

        return airSPO2(spO2);

    }


    // ---------- MAIN MEDICAL SCORE CALCULATION FUNCTION ----------
    public static int calculateMediScore(AirOxygen airOxygen, Consciousness consciousness, int respirationRange, int spo2, float temperature) {
        int score = 0;

        temperature = roundFloat(temperature); // Round float to single decimal point

        // Add score for Air or Oxygen
        score += (airOxygen == AirOxygen.OXYGEN) ? 2 : 0; // Max Score is 2

        // Add score for Consciousness
        score += (consciousness == Consciousness.ALERT) ? 0 : 1; // Max Score is 3

        // Add score for Respiration range

        score += getRespirationScore(respirationRange); // Max Score is 6

        // Add score for Temperature
        score += getTemperatureScore(temperature); // Max Score is 9

        // Add score for SPO2
        int saturationScore = getSPO2(airOxygen,spo2);
        score += saturationScore; // Max Score is 12

        // Add score for Supplementary Oxygen
        // If the saturation score if different than 0 means Patient needs Extra Oxygen.
        if (saturationScore == 0 && airOxygen == AirOxygen.OXYGEN){
            score += 2;
        }


        return score;
    }

    // ---------- MAIN FUNCTION FOR TESTING ----------

    public static void main(String[] args) {
        int patient1Score = calculateMediScore(AirOxygen.AIR, Consciousness.ALERT, 15, 95, 37.1f);
        System.out.println("Patient 1 Medi Score: " + patient1Score);

        int patient2Score = calculateMediScore(AirOxygen.OXYGEN, Consciousness.ALERT, 17, 95, 37.1f);
        System.out.println("Patient 2 Medi Score: " + patient2Score);

        int patient3Score = calculateMediScore(AirOxygen.OXYGEN, Consciousness.CVPU, 23, 88, 38.5f);
        System.out.println("Patient 3 Medi Score: " + patient3Score);

    }
}
