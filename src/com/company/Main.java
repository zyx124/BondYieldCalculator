package com.company;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.math.RoundingMode;
import java.math.BigDecimal;


/**
 * This Bond Yield Program implements an application that can
 * calculate bond price, given the coupon in percentage, the
 * time in year, the face value and the rate in decimal.
 *
 * With the coupon, the time, the face and the price,
 * it can also calculate the rate.
 *
 * @author Yuxin Zhao
 * @version 0.1
 * @since 11-21-2019
 */

public class Main {

    public static void main(String[] args) {

        String userInput;
        Scanner scan = new Scanner(System.in);

        BondYieldCalculator cal = new BondYieldCalculator();

        while (true) {
            System.out.println();
            System.out.println("*****Welcome to use the Bond Yield Calculator*****");
            System.out.println("*****Available Options*****");
            System.out.println("*. Press 1 for calculating price");
            System.out.println("*. Press 2 for calculating yield");
            System.out.println("*. Press 3 to exit");
            System.out.println("Please enter you choice: ");

            userInput = scan.nextLine();

            switch (userInput) {
                case "1":
                    cal.CalPriceInterface();
                    break;
                case "2":
                    cal.CalYieldInterface();
                    break;
                case "3":
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid input, please read the options.");
                    System.out.println();
            }
        }



    }
}



class BondYieldCalculator {
    public static final double ACCURACY = 0.0000001;
    public static final int MAX_ITER = 1000;


    public BondYieldCalculator(){ };


    /**
     * The interface for inputs to calculate Price.
     */
    public void CalPriceInterface() {
        Scanner scanForPrice = new Scanner(System.in);
        System.out.println("**Mode 1: Price Calculation");

        try{
            System.out.println("Please input the coupon in decimal: ");
            double coupon = scanForPrice.nextDouble();
            System.out.println("Please input the time in year: ");
            int year = scanForPrice.nextInt();
            System.out.println("Please input the face value: ");
            double face = scanForPrice.nextDouble();
            System.out.println("Please input the rate in decimal: ");
            double rate = scanForPrice.nextDouble();

            long start = System.currentTimeMillis();
            double res = CalcPrice(coupon, year, face, rate);
            BigDecimal bd = new BigDecimal(res).setScale(7, RoundingMode.HALF_UP);
            double finalRes = bd.doubleValue();
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println("The price is " + finalRes);
            System.out.println("The calculation time is " + time + " ms");

        } catch (InputMismatchException e) {
            System.out.println("Invalid Input!");
            System.out.println();
        }


     }


    /**
     * The interface for inputs to calculate Yield.
     */
     public void CalYieldInterface() {
         Scanner scanForYield = new Scanner(System.in);
         System.out.println("**Mode 2: Yield Calculation");

         try{
             System.out.println("Please input the coupon in decimal: ");
             double coupon = scanForYield.nextDouble();
             System.out.println("Please input the time in year: ");
             int year = scanForYield.nextInt();
             System.out.println("Please input the face value: ");
             double face = scanForYield.nextDouble();
             System.out.println("Please input the price in decimal: ");
             double price = scanForYield.nextDouble();

             long start = System.currentTimeMillis();
             double res = CalYield(coupon, year, face, price);
             BigDecimal bd = new BigDecimal(res).setScale(7, RoundingMode.HALF_UP);
             double finalRes = bd.doubleValue();
             long end = System.currentTimeMillis();
             long time = end - start;
             System.out.println("The yield is " + finalRes);
             System.out.println("The calculation time is " + time + " ms");

         } catch (InputMismatchException e) {
             System.out.println("Invalid Input!");
             System.out.println();
         }

     }


    /**
     * This method provides the method to calculate the price of a bond
     * for a given set of parameters.
     * @param coupon The coupon.
     * @param year The duration in years.
     * @param face The face value.
     * @param rate The rate/yield.
     * @return double This is the present price.
     */
    public double CalcPrice(double coupon, int year, double face, double rate) {
        double price = 0;
        price += face / Math.pow((1 + rate), year);

        for (int t = 1; t < year + 1; t++) {
            price += coupon * face / Math.pow((1 + rate), t);
        }

        return price;
    }


    /**
     * This function implements Newton Method to calculate the derivative
     * of the function with rate as variable.
     * @param coupon The coupon in decimal.
     * @param year The duration.
     * @param face The face value.
     * @param rate The present rate
     * @return The derivative value at current condition.
     */
    public double CalDerivative(double coupon, int year, double face, double rate) {
        double derivRes = -year * face / Math.pow(1 + rate, year + 1);
        for (int t = 1; t < year + 1; t++) {
            derivRes -= t * coupon * face / Math.pow(1 + rate, t + 1);
        }

        return derivRes;
    }


    /**
     * This method is for calculating the yield by using Newton's method.
     * @param coupon The coupon.
     * @param year The duration.
     * @param face The face value.
     * @param price The price given for a specific rate.
     * @return The yield in decimal number.
     */
    public double CalYield(double coupon, int year, double face, double price) {
        double rate = 0.10;
        double diff;

        int i = 0;

        do {
            double estimate = CalcPrice(coupon, year, face, rate);

            // System.out.println("estimate" + estimate);
            diff = estimate - price;
            double derivRes = CalDerivative(coupon, year, face, rate);
            // System.out.println("derive" + derivRes);
            rate -= diff / derivRes;

        } while (Math.abs(diff) > ACCURACY && i++ < MAX_ITER);

        return rate;
    }



}
