/*
 * Armour
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

enum ArmourTypes { Wearable, Hold }; //Armour can be wearable (eg helmet) or holdable (eg shield)
enum ArmourMaterial { Cardboard, Leather, Wood, Iron, Steel };

public class Armour extends Item {
    //Armour class Fields
    private int armourDefence; //The defensive value of the armour. Must be in range [1..100]
    private int armourHealth; //The health of the armour. Must be in range [1..100]
    private  boolean equipped; //A simple boolean flag. True means the armour is being held (shield) or worn (eg gloves)
    private ArmourTypes armourType;
    private ArmourMaterial armourMaterial;

    // Getters and setter
    // Throw an IllegalArgumentException if a Setter is provided with an invalid argument
    public int getArmourDefence() {
        return armourDefence;
    }


    public void setArmourDefence(int armourDefence) {
        this.armourDefence = armourDefenceExceptionCheck(armourDefence);
    }

    public int getArmourHealth() {
        return armourHealth;
    }

    public void setArmourHealth(int armourHealth) {
        this.armourHealth = armourHealthExceptionCheck(armourHealth);
    }

    public ArmourTypes getArmourType() {
        return armourType;
    }

    //public void setArmourType_(ArmourTypes armourType) { } //Armour type should not change once created

    public ArmourMaterial getArmourMaterial() {
        return armourMaterial;
    }

    //public void setArmourMaterial(ArmourMaterial armourMaterial) { } //Armour material should not change once created

    // Constructor
    // If an invalid argument is provided throw an IllegalArgumentException exception

    public Armour(String itemName, double itemWeight, double itemValue, int itemMagic, int armourDefence, int armourHealth, boolean equipped, ArmourTypes armourType, ArmourMaterial armourMaterial) {
        super(itemName, itemWeight, itemValue, itemMagic);
        this.armourDefence = armourDefenceExceptionCheck(armourDefence);
        this.armourHealth = armourHealthExceptionCheck(armourHealth);
        this.equipped = equipped;
        this.armourType = armourType;
        this.armourMaterial = armourMaterial;
    }

    // Note - You can implement additional supporting private methods if you want. Add them below this section
    // Additional methods ------------------------------------------------------------------------------------
    private int armourDefenceExceptionCheck(int defence) throws  IllegalArgumentException
    {
        if (defence <1 || defence > 100)
            throw  new IllegalArgumentException("Defence value must be in range of 1 to 100.");
        return  defence;
    }
    private int armourHealthExceptionCheck(int health) throws  IllegalArgumentException
    {
        if (health <1 || health > 100)
            throw  new IllegalArgumentException("Health value must be in range of 1 to 100.");
        return  health;
    }

    //These public methods were missing but required
    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
