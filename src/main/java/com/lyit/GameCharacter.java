/*
 * GameCharacter
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


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

enum CharacterState { Idle, Running, Sleeping, Walking, Defending, Dead, Eating, Attacking };

// Note - If the character state is Dead, the character should not be able to do anything.
// Invoking a method when the character is dead should do nothing

//public abstract class GameCharacter {
public class GameCharacter {
    //Attributes
    private String characterName; //Cannot be empty
    private int health; //Must be in range [0..100]. If 0 then character state should be dead. If > 0 the character cannot be dead
    private double weightLimit; //Must be > 0
    private double totalWeightOfItems; //Cannot exceed weightLimit


    // GameCharacter inventory
    // NOTE: The GameCharacter's inventory (items, food, weapons, armour) must be stored within the class.
    // You need to select appropriate collection(s) to allow the character to pickup and drop game items.

    //Decided to use ArrayList parent 'Item' for inventory, then use polymorphism as required.
    private ArrayList<Item> inventory;

    private CharacterState characterState;

    // Getters and setters
    // Setters should be modified to deal with invalid arguments. Throw an IllegalArgumentException if
    // the setter is provided with an invalid argument

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterNameCheck(characterName);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = healthCheck(health);
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimitCheck(weightLimit);
    }

    public double getTotalWeightOfItems() {
        return totalWeightOfItems;
    }

    public void setTotalWeightOfItems(double totalWeightOfItems) {
        this.totalWeightOfItems = itemsOverWeightLimitExceptionCheck(totalWeightOfItems);
    }

    public CharacterState getCharacterState() {
        return characterState;
    }

    public void setCharacterState(CharacterState characterState) {
        this.characterState = characterStateCheck(characterState);
    }


    // GameCharacter Constructor
    // If an invalid argument is provided throw an IllegalArgumentException exception
    public GameCharacter(String characterName, int health, double weightLimit, CharacterState characterState) {
        this.characterName = characterNameCheck(characterName);
        this.health = healthCheck(health);
        this.weightLimit = weightLimitCheck(weightLimit);
        this.characterState =  characterState;
        this.inventory = new ArrayList<Item>();
        //TODO - Complete this method
    }


    // Logic for the eat method
    // If the item at inventoryItemIndex is of the class Food, the item is consumed and removed from the inventory.
    // Eating the item should increase the characters health. 1 unit of health = 25 calories.
    // The state of the food modifies the calorie content for only food with positive calorie values, as follows:
    // Fresh 100% of calories, stale 60%, Mouldy 40%, rotten 10%
    // Eg. an apple is 52 calories per 100g. 100g of mouldy apple will give only 20.8 (52*.4) calories.
    // Health should not exceed 100 which is full health for the character.
    // Consuming poisonous food (food with a negative calorie value) should reduce the health accordingly.
    // When the eat method is invoked, the character's status should also change to "Eating"
    // Return true if the item was food and consumed, otherwise false
    public boolean eat(int inventoryFoodIndex){
        boolean isEaten = false;
        if (!isDead()) {
            Item itemAtIndex = inventory.get(inventoryFoodIndex);

            if (itemAtIndex instanceof Food) {
                Food food = (Food) itemAtIndex;
                double totalCalories = food.calories * food.getItemWeight() *10;
                if (totalCalories > 0)
                {
                    switch (food.getFoodState())
                    {
                        case Stale:
                            totalCalories = totalCalories * 0.6;
                            break;
                        case Mouldy:
                            totalCalories = totalCalories * 0.4;
                            break;
                        case Rotten:
                            totalCalories = totalCalories * 0.1;
                            break;
                    }
                }
                int healthAdjustment = (int) (totalCalories / 25.0);
                int newHealth;
                int currentHealth = getHealth();
                if (healthAdjustment >= 0)
                {
                    newHealth = Math.min(100, currentHealth + healthAdjustment);
                }
                else
                {
                    newHealth = Math.max(0, currentHealth + healthAdjustment);
                }
                setHealth(newHealth);
                dropItem(food);
                isEaten = true;
                setCharacterState(CharacterState.Eating);
            }

        }
        return isEaten;
    }

    // The attack method should  return a string with the following information:
    // "<this.getName()> is attacking targetCharacter.getName() with a <equippedWeapon.getItemName()>
    // Example output “Lord Percival is attacking Lord Blackadder with a sword"
    // If the attacker is using two weapons:
    // Example output “Lord Percival is attacking Lord Blackadder with a sword and a knife"

    public String attack(GameCharacter targetCharacter) {
        //TODO - Complete this method
        String returnString = "";
        String characterName = getCharacterName();
        String enemyName = targetCharacter.getCharacterName();

        ArrayList<String> weapons = new ArrayList<>();
        for (Item item :
                inventory) {
            if (item instanceof Weapon) {
                Weapon weaponCheck = (Weapon) item;
                if (weaponCheck.isWeaponEquipped()) {
                    weapons.add(weaponCheck.getItemName());
                }
            }
        }
        if (weapons.size() > 0)
            setCharacterState(CharacterState.Attacking);
        switch (weapons.size())
        {
            case 1:
                returnString = String.format("%s is attacking %s with a %s", characterName,enemyName, weapons.get(0));
                break;
            case 2:
                returnString = String.format("%s is attacking %s with a %s and a %s", characterName,enemyName, weapons.get(0), weapons.get(1));
                break;
        }
        return returnString;
    }

    // The equipWeapon will equip the weapon at inventoryWeaponIndex
    // If the item in the inventory index is a weapon the method should return true, otherwise false
    // If the weapon requires both hands (doubleHanded = true) the character must unequip any weapons or shields
    // they are currently holding.
    public boolean equipWeapon(int inventoryWeaponIndex) {


        boolean isEquipped = false;
        if (!isDead()) {
            //Decided to add a check if more than two weapons equipped
            int count = 0;
            for (Item item :
                    inventory) {
                if (item instanceof Weapon) {
                    Weapon weaponCheck = (Weapon) item;
                    if (weaponCheck.isWeaponEquipped())
                        count++;
                }
            }

            if (count < 2 && inventoryWeaponIndex < inventory.size()) {
                Item itemAtIndex = inventory.get(inventoryWeaponIndex);

                if (itemAtIndex instanceof Weapon) {
                    Weapon weapon = (Weapon) itemAtIndex;
                    weapon.setWeaponEquipped(true);
                    isEquipped = true;

                    if (weapon.isDoubleHanded()) {
                        for (Item item :
                                inventory) {
                            if (item instanceof Armour) {
                                Armour shieldCheck = (Armour) item;
                                if (shieldCheck.getArmourType().equals(ArmourTypes.Hold) && shieldCheck.isEquipped())
                                    shieldCheck.setEquipped(false);
                            }
                        }

                    }
                }

            }
        }
        return isEquipped;
    }



    // If the item in the inventory at inventoryWeaponIndex is a weapon, unequip it and return true, otherwise false
    // Weapons that are unequipped remain in the inventory.
    public boolean unEquipWeapon(int inventoryWeaponIndex) {

        boolean isUnequipped = false;
        if (!isDead()) {
            if (inventoryWeaponIndex < inventory.size()) {
                Item itemAtIndex = inventory.get(inventoryWeaponIndex);

                if (itemAtIndex instanceof Weapon) {
                    Weapon weapon = (Weapon) itemAtIndex;
                    weapon.setWeaponEquipped(false);
                    isUnequipped = true;
                }

            }
        }
        return isUnequipped;
    }

    // The equipArmour method should equip the item of armour at armourIndex in the inventory
    // The character can only equip one shield at a time. If a shield is already equipped and the armourIndex
    // argument points to another shield, then the shield at armourIndex is equipped instead. If the armourIndex points
    // to a wearable piece of armour the GameCharacter should put it on. The character may wear multiple pieces of armour
    public boolean equipArmour(int armourIndex) {
        boolean isEquipped = false;
        if (!isDead()) {
            if (armourIndex < inventory.size()) {
                Item itemAtIndex = inventory.get(armourIndex);

                if (itemAtIndex instanceof Armour) {
                    Armour armour = (Armour) itemAtIndex;

                    if (armour.getArmourType().equals(ArmourTypes.Hold)) {
                        for (Item item :
                                inventory) {
                            if (item instanceof Armour)
                            {
                                Armour checkArmour = (Armour)item;
                                if (checkArmour.getArmourType().equals(ArmourTypes.Hold) && checkArmour.isEquipped())
                                    checkArmour.setEquipped(false);
                            }
                        }
                    }

                    armour.setEquipped(true);
                    isEquipped = true;
                }
            }

        }

        return isEquipped;
    }

    // The method should remove the item of armour at armourIndex from the gameCharacter. The character essentially
    // takes off the piece of armour but it remains in their inventory
    public void removeArmour(int armourIndex){

        if (!isDead()) {
            if (armourIndex < inventory.size()) {
                Item itemAtIndex = inventory.get(armourIndex);

                if (itemAtIndex instanceof Armour) {
                    Armour armour = (Armour) itemAtIndex;
                    armour.setEquipped(false);
                }

            }
        }

    }

    // The defend method should set the characters state to defending
    public void defend(){
        if (!isDead())
            setCharacterState(CharacterState.Defending);
    }

    // The method should change the characters state to walking
    public void walk(){
        //TODO - Complete this method
        if (!isDead())
            setCharacterState(CharacterState.Walking);
    }

    // The method should change the characters state to running
    public void run(){
        //TODO - Complete this method
        if (!isDead())
            setCharacterState(CharacterState.Running);
    }

    // The method should change the characters state to sleeping
    // The character's health should increase by 20 but should not exceed the maximum (100)
    public void sleep(){
        //TODO - Complete this method
        if (!isDead()) {
            setCharacterState(CharacterState.Sleeping);
            int currentHealth = getHealth();
            int newHealth = Math.min(currentHealth+20,100);
            setHealth(newHealth);

        }
    }

    // If the character is sleeping the wakeUp method should change the character's state to Idle
    // If the character is not sleeping, the method should not change the character's state.
    public void wakeUp(){
        //TODO - Complete this method
        if (getCharacterState().equals(CharacterState.Sleeping))
            setCharacterState(CharacterState.Idle);
    }

    // The pickUpItem method should add the item to the game characters inventory if the item does not exceed the
    // current weightLimit for the character. If the character does pick up the item the weight of the item
    // should be added to the total weight of items carried by the character
    // The item object may be an instance of the Item or any subclass (armour, weapon or food)
    public void pickUpItem(Item item){
        if (!isDead())
        {
            if (item.getItemWeight() + getTotalWeightOfItems() <= getWeightLimit() )
            {
                inventory.add(item);
                setTotalWeightOfItems(roundToFourDecimal(item.getItemWeight() + getTotalWeightOfItems()));
            }
        }
        //TODO - Complete this method
    }

    // The dropItem method should remove the item from the game character's inventory. The total weight of items
    // should be updated accordingly.
    // The item object may be an instance of the Item or any subclass (armour, weapon or food)
    public void dropItem(Item item){
        if (!isDead())
        {
            inventory.remove(item);
            setTotalWeightOfItems(roundToFourDecimal(getTotalWeightOfItems() - item.getItemWeight()));
        }
        //TODO - Complete this method
    }

    // The defence value is determined by totalling the armourDefence values of all armour items currently equipped
    // by the game character
    public double getCharacterDefence() {
        int defenceValue = 0;
        for (Item item :
                inventory) {
            if (item instanceof Armour) {
                Armour armourCheck = (Armour) item;
                if (armourCheck.isEquipped())
                    defenceValue += armourCheck.getArmourDefence();

            }
        }
        return  defenceValue;
    }

    // The attack value is determined by totalling the weaponHitStrength values of all weapons currently equipped
    // by the game character
    public double getCharacterAttackValue() {
        int attackValue = 0;
        for (Item item :
                inventory) {
            if (item instanceof Weapon) {
                Weapon weaponCheck = (Weapon) item;
                if (weaponCheck.isWeaponEquipped())
                    attackValue += weaponCheck.getWeaponHitStrength();

            }
        }
        return  attackValue;
    }

    // Note - You can implement additional supporting private methods if you want. Add them below this section
    // Additional methods ------------------------------------------------------------------------------------
    private String characterNameCheck(String name) throws  IllegalArgumentException
    {
        if (isDead())
            return getCharacterName();
        if (name == null || name.trim().isEmpty())
            throw  new IllegalArgumentException("Item Name cannot be empty");
        return  name;
    }
    private int healthCheck(int healthCheck) throws  IllegalArgumentException
    {
        if (isDead())
            return 0;
        if (healthCheck <0 || healthCheck > 100)
            throw  new IllegalArgumentException("Health value must be in range of 0 to 100.");
        if (healthCheck == 0)
            setCharacterState(CharacterState.Dead);
        return  healthCheck;
    }
    private double weightLimitCheck(double weightLimit) throws  IllegalArgumentException
    {
        if (isDead())
            return getWeightLimit();
        if (weightLimit <= 0 )
            throw  new IllegalArgumentException("Weight limit value must be greater than 0.");
        return  weightLimit;
    }
    private double itemsOverWeightLimitExceptionCheck(double totalWeight) throws  IllegalArgumentException
    {
        if (isDead())
            return getTotalWeightOfItems();
        if (totalWeight > getWeightLimit())
            throw  new IllegalArgumentException("Total weight of items cannot be greater than the total weight limit.");
        return  totalWeight;
    }
    private boolean isDead()
    {
        if (getCharacterState() == null)
            return false;
        return getCharacterState().equals(CharacterState.Dead);
    }
    private CharacterState characterStateCheck(CharacterState state)
    {
        if (isDead())
            return  CharacterState.Dead;
        else
            return  state;
    }
    private double roundToFourDecimal(double value)
    {
        return (double)Math.round(value * 10000d) / 10000d;

    }


}
