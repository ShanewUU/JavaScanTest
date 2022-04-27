/*
 * Item
 *
 * Version information
 *
 * Date 30/01/2021
 *
 * Author: Dr. Shane Wilson
 *
 * Copyright notice
 */

package com.lyit;

public class Item {
    private String itemName;  //Cannot be empty
    private double itemWeight; //Must be > 0 . Richard - I am going to assume weight is in Kg as it is a double.
    private double itemValue; //Must >=0
    private int itemMagicValue; //Must >=0

    // Getters and Setters
    // Throw an IllegalArgumentException if a Setter is provided with an invalid argument
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemNameExceptionCheck(itemName);
    }

    public double getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(double itemWeight) {
        this.itemWeight = itemWeightExceptionCheck(itemWeight);
    }

    public double getItemValue() {
        return itemValue;
    }

    public void setItemValue(double itemValue) {
        this.itemValue = itemValueExceptionCheck(itemValue);
    }

    public int getItemMagicValue() {
        return itemMagicValue;
    }

    public void setItemMagicValue(int itemMagicValue) {
        this.itemMagicValue = itemMagicValueExceptionCheck(itemMagicValue);
    }

    // Constructor
    // If an invalid argument is provided throw an IllegalArgumentException exception
    public Item(String itemName, double itemWeight, double itemValue, int itemMagic) {
        this.itemName = itemNameExceptionCheck(itemName);
        this.itemWeight = itemWeightExceptionCheck(itemWeight);
        this.itemValue = itemValueExceptionCheck(itemValue);
        this.itemMagicValue = itemMagicValueExceptionCheck(itemMagic);

    }

    // Note - You can implement additional supporting private methods if you want. Add them below this section
    // Additional methods ------------------------------------------------------------------------------------
    private String itemNameExceptionCheck(String name) throws  IllegalArgumentException
    {
        if (name == null || name.trim().isEmpty())
            throw  new IllegalArgumentException("Item Name cannot be empty");
        return  name;
    }

    private double itemWeightExceptionCheck(double weight) throws  IllegalArgumentException
    {
        if (weight <= 0)
            throw  new IllegalArgumentException("Weight must be greater than 0");
        return  weight;
    }
    private double itemValueExceptionCheck(double value) throws  IllegalArgumentException
    {
        if (value < 0)
            throw  new IllegalArgumentException("Item value must be greater or equal to 0");
        return  value;
    }

    private int itemMagicValueExceptionCheck(int magicValue) throws  IllegalArgumentException
    {
        if (magicValue < 0)
            throw  new IllegalArgumentException("Item magic value must be greater or equal to 0");
        return  magicValue;
    }
}
