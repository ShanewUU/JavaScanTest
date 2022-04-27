package com.lyit;

import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;


public class GameCharacterTest {


    GameCharacter defaultCharacter;
    @BeforeMethod(groups = {"setterTest","constructorTest","otherGroup"})
    public void initialiseDefaultCharacter()
    {
        defaultCharacter = new GameCharacter("Gnark", 75, 50, CharacterState.Idle);
    }

    @DataProvider(name = "characterConstructorFail")
    public Object[][]characterConstructorDataProviderFail()
    {
        return new Object[][]{
                {"",100,10.25,CharacterState.Idle},//No name
                {"Gnark",-1,10.25,CharacterState.Idle},//Health too low
                {"Gnark",101,10.25,CharacterState.Idle},//Health too high
                {"Gnark",100,0,CharacterState.Idle},//No weight limit
        };
    }
    @DataProvider(name = "characterConstructorPass")
    public Object[][]characterConstructorDataProviderPass()
    {
        return new Object[][]{
                {"Gnark",1,1,CharacterState.Idle},
                {"Gnark",100,10.25,CharacterState.Idle},
        };
    }

    //To check an exception not called
    @Test(dataProvider = "characterConstructorPass",groups = "constructorTest")
    public void characterConstructor_ValidConstructor_Passed(String name, int health, double weightLimit, CharacterState characterState)
    {
            GameCharacter testCharacter = new GameCharacter(name,health,weightLimit,characterState);
    }


    @Test(dataProvider = "characterConstructorFail",expectedExceptions = IllegalArgumentException.class,groups = "constructorTest")
    public void characterConstructor_InvalidConstructor_ExpectException(String name, int health, double weightLimit, CharacterState characterState)
    {
        GameCharacter testCharacter = new GameCharacter(name,health,weightLimit,characterState);
    }


    @DataProvider(name = "characterNameFail")
    public Object[][]itemNameDataProviderFail()
    {
        return new Object[][]{
                {null},
                {""},
                {" "}
        };
    }

    @Test(dataProvider = "characterNameFail", expectedExceptions = IllegalArgumentException.class,groups = "setterTest")
    public void setCharacterName_EmptyValue_ExpectException(String itemName) {
        defaultCharacter.setCharacterName(itemName);
    }

    @Test(groups = "setterTest")
    public void setItemName_CorrectValue_Passed()
    {
        //Arrange

        defaultCharacter.setCharacterName("Bob the Dwarf");
        String expectedResult = "Bob the Dwarf";
        String actualResult;

        //Act
        actualResult = defaultCharacter.getCharacterName();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);
    }

    @Test(groups = "setterTest", expectedExceptions = IllegalArgumentException.class)
    public void setCharacterHealth_OverLimit_ExpectException() {
        defaultCharacter.setHealth(101);
    }

    @Test(groups = "setterTest", expectedExceptions = IllegalArgumentException.class)
    public void setCharacterHealth_UnderLimit_ExpectException() {
        defaultCharacter.setHealth(-1);
    }



    @DataProvider(name = "validRangeDataProvider")
    public Object[][]validRangesDataProvider()
    {
        return new Object[][]{
                {1},
                {100},
                {50}
        };
    }

    @Test(groups = "setterTest", dataProvider = "validRangeDataProvider")
    public void setCharacterHealth_CorrectValue_Passed(int health) {

        //Arrange

        defaultCharacter.setHealth(health);
        double expectedResult = health;
        double actualResult;

        //Act
        actualResult = defaultCharacter.getHealth();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);

    }

    @Test(groups = "setterTest", expectedExceptions = IllegalArgumentException.class)
    public void setCharacterWeightLimit_UnderLimit_ExpectException()
    {
        defaultCharacter.setWeightLimit(0.0);
    }

    @Test(groups = "setterTest", expectedExceptions = IllegalArgumentException.class)
    public void setTotalWeightItems_OverWeightLimit_ExpectException()
    {
        double weightLimit = defaultCharacter.getWeightLimit();
        defaultCharacter.setTotalWeightOfItems(weightLimit + 0.1);
    }

    @Test(groups = "setterTest")
    public void setTotalWeightItems_WithinWeightLimit_Passed()
    {
        double weightLimit = defaultCharacter.getWeightLimit();
        defaultCharacter.setTotalWeightOfItems(weightLimit);

        double weightItems = defaultCharacter.getTotalWeightOfItems();

        Assert.assertTrue(weightLimit >= weightItems);
    }
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void eat_FoodAtIndex_Passed()
    {
        boolean expectedValue = true;
        boolean actualValue;
        defaultCharacter.pickUpItem(new Food("Apple", 0.2, 1,0, FoodState.Fresh, 182));

        actualValue = defaultCharacter.eat(0);

        Assert.assertEquals(actualValue,expectedValue);

    }
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void eat_FoodNotAtIndex_DoesNotEat()
    {
        boolean expectedValue = false;
        boolean actualValue;
        defaultCharacter.pickUpItem(new Food("Apple", 0.2, 1,0, FoodState.Fresh, 182));
        defaultCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));

        actualValue = defaultCharacter.eat(1);

        Assert.assertEquals(actualValue,expectedValue);

    }
    @DataProvider(name = "validEatData")
    public Object[][]validEatDataProvider()
    {
        return new Object[][]{
                {FoodState.Fresh, 200,91 },
                {FoodState.Stale, 200,84},
                {FoodState.Mouldy, 200,81 },
                {FoodState.Rotten, 200,76 },
                {FoodState.Fresh, -200, 59},
                {FoodState.Stale, -200,59},
                {FoodState.Mouldy, -200,59 },
                {FoodState.Rotten, -200,59 }

        };
    }
    @Test(dataProvider = "validEatData",groups = "otherGroup")
    public void eat_ChangeHealth_HealthByCorrectAmount(FoodState foodState,int calories, int expectedHealth)
    {
        int actualHealth;
        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, foodState, calories));
        defaultCharacter.eat(0);

        actualHealth = defaultCharacter.getHealth();

        Assert.assertEquals(actualHealth, expectedHealth);

    }
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void eat_HealthAtMax_NoChangeHealth()
    {
        int actualHealth;
        int expectedHealth = 100;

        defaultCharacter.setHealth(100);
        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));
        defaultCharacter.eat(0);

        actualHealth = defaultCharacter.getHealth();

        Assert.assertEquals(actualHealth, expectedHealth);
    }
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void eat_PoisonHealthToZero_NoHealth()
    {
        int actualHealth;
        int expectedHealth = 0;

        defaultCharacter.setHealth(10);
        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, -200));
        defaultCharacter.eat(0);
        actualHealth = defaultCharacter.getHealth();

        Assert.assertEquals(actualHealth, expectedHealth);
    }
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void eat_ChangeCharacterState_SetToEating()
    {
        CharacterState actualValue;
        CharacterState expectedValue = CharacterState.Eating;

        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));

        defaultCharacter.eat(0);
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue, expectedValue);
    }

    //Depends on pickupitem
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void eat_FoodEaten_ItemDropped()
    {
        double actualWeight;
        double expectedWeight = 0;

        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));
        defaultCharacter.eat(0);
        actualWeight = defaultCharacter.getTotalWeightOfItems();

        Assert.assertEquals(actualWeight, expectedWeight);

    }


    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit","equipWeapon_WeaponAtIndex_True"})
    public void attack_OneWeapon_CorrectString()
    {
        String expectedValue = "Gnark is attacking Lord Blackadder with a Sword";
        String actualValue;

        GameCharacter attackedCharacter = new GameCharacter("Lord Blackadder", 75, 50, CharacterState.Defending);
        defaultCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));
        defaultCharacter.equipWeapon(0);
        actualValue = defaultCharacter.attack(attackedCharacter);

        Assert.assertEquals(actualValue, expectedValue);

    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit","equipWeapon_WeaponAtIndex_True"})
    public void attack_TwoWeapons_CorrectString()
    {
        String expectedValue = "Gnark is attacking Lord Blackadder with a Sword and a Knife";
        String actualValue;

        GameCharacter attackedCharacter = new GameCharacter("Lord Blackadder", 75, 50, CharacterState.Defending);
        defaultCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));
        defaultCharacter.pickUpItem(new Weapon("Knife", 1, 2,2,25,25,false));
        defaultCharacter.equipWeapon(0);
        defaultCharacter.equipWeapon(1);
        actualValue = defaultCharacter.attack(attackedCharacter);

        Assert.assertEquals(actualValue, expectedValue);

    }
    @Test(groups = "otherGroup",dependsOnMethods = {"pickUpItem_AddItem_BelowLimit","equipWeapon_WeaponAtIndex_True"})
    public void attack_ChangeState_CorrectState()
    {
        CharacterState expectedValue = CharacterState.Attacking;
        CharacterState actualValue;

        GameCharacter attackedCharacter = new GameCharacter("Lord Blackadder", 75, 50, CharacterState.Defending);
        defaultCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));
        defaultCharacter.equipWeapon(0);
        defaultCharacter.attack(attackedCharacter);
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue, expectedValue);

    }


    @Test(groups = "otherGroup")
    public void attack_NoWeapons_CorrectString()
    {
        String expectedValue = "";
        String actualValue;

        GameCharacter attackedCharacter = new GameCharacter("Lord Blackadder", 75, 50, CharacterState.Defending);
        actualValue = defaultCharacter.attack(attackedCharacter);

        Assert.assertEquals(actualValue, expectedValue);

    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void equipWeapon_WeaponAtIndex_True()
    {
        boolean expectedValue = true;
        boolean actualValue;

        defaultCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));
        actualValue = defaultCharacter.equipWeapon(0);

        Assert.assertEquals(actualValue, expectedValue);


    }
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void equipWeapon_WeaponNotAtIndex_False()
    {
        boolean expectedValue = false;
        boolean actualValue;

        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));
        actualValue = defaultCharacter.equipWeapon(0);

        Assert.assertEquals(actualValue, expectedValue);


    }

    //Depends on get character defence
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void equipWeapon_TwoHandedWeapon_ShieldUnequipped()
    {
        double actualValue;
        double expectedValue = 0.0;

        defaultCharacter.pickUpItem(new Armour("Leather Shield", 5, 5, 0, 10, 10, true,ArmourTypes.Hold, ArmourMaterial.Leather));

        defaultCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,true));
        defaultCharacter.equipWeapon(1);

        actualValue = defaultCharacter.getCharacterDefence();

        Assert.assertEquals(actualValue, expectedValue);


    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void unEquipWeapon_WeaponAtIndex_True()
    {
        boolean expectedValue = true;
        boolean actualValue;

        defaultCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));
        defaultCharacter.equipWeapon(0);
        actualValue = defaultCharacter.unEquipWeapon(0);

        Assert.assertEquals(actualValue, expectedValue);


    }
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void unEquipWeapon_WeaponNotAtIndex_False()
    {
        boolean expectedValue = false;
        boolean actualValue;

        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));
        actualValue = defaultCharacter.unEquipWeapon(0);

        Assert.assertEquals(actualValue, expectedValue);


    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void equipArmour_ArmourAtIndex_True()
    {
        boolean expectedValue = true;
        boolean actualValue;

        defaultCharacter.pickUpItem(new Armour("Leather Shield", 5, 5, 0, 10, 10, false,ArmourTypes.Hold, ArmourMaterial.Leather));
        actualValue = defaultCharacter.equipArmour(0);

        Assert.assertEquals(actualValue, expectedValue);


    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void equipArmour_ArmourNotAtIndex_False()
    {
        boolean expectedValue = false;
        boolean actualValue;

        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));
        actualValue = defaultCharacter.equipArmour(0);

        Assert.assertEquals(actualValue, expectedValue);


    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void equipArmour_ShieldAlreadyEquipped_DefenceChanged()
    {
        double expectedValue = 20.0;
        double actualValue;

        defaultCharacter.pickUpItem(new Armour("Leather Shield", 5, 5, 0, 10, 10, true,ArmourTypes.Hold, ArmourMaterial.Leather));
        defaultCharacter.pickUpItem(new Armour("Iron Kite Shield", 15, 15, 0, 20, 20, false,ArmourTypes.Hold, ArmourMaterial.Iron));
        defaultCharacter.equipArmour(1);

        actualValue = defaultCharacter.getCharacterDefence();

        Assert.assertEquals(actualValue, expectedValue);


    }
    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void unEquipArmour_ArmourAtIndex_DefenceChanged()
    {
        double expectedValue = 20.0;
        double actualValue;

        defaultCharacter.pickUpItem(new Armour("Iron Helmet", 5, 5, 0, 10, 10, true,ArmourTypes.Wearable, ArmourMaterial.Iron));
        defaultCharacter.pickUpItem(new Armour("Iron Gaunlets", 15, 15, 0, 20, 20, true,ArmourTypes.Wearable, ArmourMaterial.Iron));

        defaultCharacter.removeArmour(0);
        actualValue = defaultCharacter.getCharacterDefence();

        Assert.assertEquals(actualValue, expectedValue);


    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void unEquipArmour_ArmourNotAtIndex_DefenceNotChanged()
    {
        double expectedValue = 10.0;
        double actualValue;

        defaultCharacter.pickUpItem(new Armour("Iron Helmet", 5, 5, 0, 10, 10, true,ArmourTypes.Wearable, ArmourMaterial.Iron));
        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));
        defaultCharacter.removeArmour(1);

        actualValue = defaultCharacter.getCharacterDefence();

        Assert.assertEquals(actualValue, expectedValue);

    }

    @Test(groups = "otherGroup")
    public void defend_SetDefending_CorrectState()
    {
        CharacterState expectedValue = CharacterState.Defending;
        CharacterState actualValue;

        defaultCharacter.defend();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup")
    public void walk_SetWalking_CorrectState()
    {
        CharacterState expectedValue = CharacterState.Walking;
        CharacterState actualValue;

        defaultCharacter.walk();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup")
    public void run_SetRunning_CorrectState()
    {
        CharacterState expectedValue = CharacterState.Running;
        CharacterState actualValue;

        defaultCharacter.run();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup")
    public void sleep_SetSleeping_CorrectState()
    {
        CharacterState expectedValue = CharacterState.Sleeping;
        CharacterState actualValue;

        defaultCharacter.sleep();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup")
    public void sleep_HealthIncrease_CorrectHealth()
    {
        int expectedValue = 95;
        int actualValue;

        defaultCharacter.sleep();
        actualValue = defaultCharacter.getHealth();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup")
    public void sleep_HealthIncrease_HealthAtMaximum()
    {
        int expectedValue = 100;
        int actualValue;

        defaultCharacter.setHealth(95);

        defaultCharacter.sleep();
        actualValue = defaultCharacter.getHealth();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup")
    public void wakeUp_Asleep_ChangeToIdle()
    {
        CharacterState expectedValue = CharacterState.Idle;
        CharacterState actualValue;

        defaultCharacter.sleep();
        defaultCharacter.wakeUp();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup")
    public void wakeUp_NotAsleep_NoChangeToState()
    {
        CharacterState expectedValue = CharacterState.Running;
        CharacterState actualValue;

        defaultCharacter.run();
        defaultCharacter.wakeUp();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue, expectedValue);

    }

    @Test(groups = "otherGroup")
    public void pickUpItem_AddItem_BelowLimit()
    {
        double expectedValue = 1.0;
        double actualValue;

        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));
        defaultCharacter.pickUpItem(new Item("Compass", 0.8,10,10));

        actualValue = defaultCharacter.getTotalWeightOfItems();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup")
    public void pickUpItem_NotAddItem_AboveLimit()
    {
        double expectedValue = 0.2;
        double actualValue;

        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));
        defaultCharacter.pickUpItem(new Item("Ball and chain", 50,10,10));

        actualValue = defaultCharacter.getTotalWeightOfItems();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup")
    public void dropItem_RemoveItem_CorrectValue()
    {
        double expectedValue = 0.2;
        double actualValue;

        Item compass = new Item("Compass", 0.8,10,10);
        defaultCharacter.pickUpItem(compass);
        defaultCharacter.pickUpItem(new Food("Bread", 0.2, 1,0, FoodState.Fresh, 200));
        defaultCharacter.dropItem(compass);

        actualValue = defaultCharacter.getTotalWeightOfItems();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void getCharacterDefence_EquipArmour_CorrectValue()
    {
        double expectedValue = 60.0;
        double actualValue;

        defaultCharacter.pickUpItem(new Armour("Iron Helmet", 5, 5, 0, 10, 10, false,ArmourTypes.Wearable, ArmourMaterial.Iron));
        defaultCharacter.pickUpItem(new Armour("Iron Gaunlets", 15, 15, 0, 20, 20, false,ArmourTypes.Wearable, ArmourMaterial.Iron));
        defaultCharacter.pickUpItem(new Armour("Iron Shield", 15, 15, 0, 50, 20, false,ArmourTypes.Hold, ArmourMaterial.Iron));
        defaultCharacter.equipArmour(0);
        defaultCharacter.equipArmour(2);

        actualValue = defaultCharacter.getCharacterDefence();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void getCharacterDefence_UnEquipArmour_CorrectValue()
    {
        double expectedValue = 50.0;
        double actualValue;

        defaultCharacter.pickUpItem(new Armour("Iron Helmet", 5, 5, 0, 10, 10, true,ArmourTypes.Wearable, ArmourMaterial.Iron));
        defaultCharacter.pickUpItem(new Armour("Iron Shield", 15, 15, 0, 50, 20, true,ArmourTypes.Hold, ArmourMaterial.Iron));
        defaultCharacter.equipArmour(0);
        defaultCharacter.equipArmour(1);
        defaultCharacter.removeArmour(0);

        actualValue = defaultCharacter.getCharacterDefence();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void getCharacterAttackValue_EquipWeapons_CorrectValue()
    {
        double expectedValue = 75.0;
        double actualValue;

        defaultCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));
        defaultCharacter.pickUpItem(new Weapon("Knife", 1, 2,2,25,25,false));
        defaultCharacter.equipWeapon(0);
        defaultCharacter.equipWeapon(1);

        actualValue = defaultCharacter.getCharacterAttackValue();

        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(groups = "otherGroup", dependsOnMethods = {"pickUpItem_AddItem_BelowLimit"})
    public void getCharacterAttackValue_UnEquipWeapons_CorrectValue()
    {
        double expectedValue = 25.0;
        double actualValue;

        defaultCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));
        defaultCharacter.pickUpItem(new Weapon("Knife", 1, 2,2,25,25,false));
        defaultCharacter.equipWeapon(0);
        defaultCharacter.equipWeapon(1);
        defaultCharacter.unEquipWeapon(0);

        actualValue = defaultCharacter.getCharacterAttackValue();

        Assert.assertEquals(actualValue, expectedValue);
    }













}