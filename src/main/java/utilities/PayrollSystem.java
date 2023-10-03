/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author pukarsharma
 */
import java.util.List;

public class PayrollSystem {

    // Define a class to represent a tax bracket with a range and rate
    public static class TaxBracket {

        private final double minIncome;
        private final double maxIncome;
        private double rate;

        public TaxBracket(double minIncome, double maxIncome, double rate) {
            this.minIncome = minIncome;
            this.maxIncome = maxIncome;
            this.rate = rate;
        }

        public double getMinIncome() {
            return minIncome;
        }

        public double getMaxIncome() {
            return maxIncome;
        }

        public double getRate() {
            return rate;
        }
    }

    // Calculate income tax based on tax brackets
    public static double calculateIncomeTax(double taxableIncome, List<TaxBracket> taxBrackets) {
        double tax = 0.0;
        for (TaxBracket bracket : taxBrackets) {
            if (taxableIncome >= bracket.getMinIncome() && taxableIncome <= bracket.getMaxIncome()) {
                // Calculate tax within this bracket
                tax += (taxableIncome - bracket.getMinIncome()) * bracket.getRate();
                break; 
            } else if (taxableIncome > bracket.getMaxIncome()) {
                tax += (bracket.getMaxIncome() - bracket.getMinIncome()) * bracket.getRate();
            }
        }
        return tax;
    }
}
