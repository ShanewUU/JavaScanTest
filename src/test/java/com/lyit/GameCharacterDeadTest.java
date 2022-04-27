package com.lyit;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GameCharacterDeadTest {

    GameCharacter defaultCharacter;

    @BeforeTest
    public void initialiseDefaultCharacterDead()
    {
        defaultCharacter = new GameCharacter("David the Defeated", 0, 50, CharacterState.Dead);

    }

    @Test
    public void checkCharacterDead_HealthAtZero_Passed()
    {
        GameCharacter testCharacter = new GameCharacter("Robert the over-confident", 1, 50, CharacterState.Attacking);

        CharacterState expectedState = CharacterState.Dead;
        CharacterState actualState;

        testCharacter.setHealth(0);
        actualState = testCharacter.getCharacterState();

        Assert.assertEquals(actualState,expectedState);
    }

    /*These following tests are based on the criteria 'Invoking a method when the character is dead should do nothing'
    I have taken this to be literally any method i.e. renaming a character, setting health etc. I am assuming the
    character cannot be revived in this game.
     */

    @Test
    public void characterDead_SetName_NoChange() {

        String expectedValue = defaultCharacter.getCharacterName();
        String actualValue;

        defaultCharacter.setCharacterName("David the un-killable");
        actualValue = defaultCharacter.getCharacterName();

        Assert.assertEquals(actualValue,expectedValue);


    }

    @Test
    public void characterDead_SetHealth_NoChange() {

        double expectedValue = 0;
        double actualValue;

        defaultCharacter.setHealth(100);
        actualValue = defaultCharacter.getHealth();

        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_SetWeightLimit_NoChange() {
        double expectedValue = defaultCharacter.getWeightLimit();
        double actualValue;

        defaultCharacter.setWeightLimit(100);
        actualValue = defaultCharacter.getWeightLimit();

        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_SetTotalWeightItems_NoChange() {

        double expectedValue = defaultCharacter.getTotalWeightOfItems();
        double actualValue;

        defaultCharacter.setTotalWeightOfItems(100);
        actualValue = defaultCharacter.getTotalWeightOfItems();

        Assert.assertEquals(actualValue,expectedValue);

    }

    @Test
    public void characterDead_SetCharacterState_NoChange() {

        CharacterState expectedValue = CharacterState.Dead;
        CharacterState actualValue;

        defaultCharacter.setCharacterState(CharacterState.Idle);
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue,expectedValue);
    }

    //Should depend on eat test
    @Test
    public void characterDead_Eat_NoChange() {


        boolean expectedValue = false;
        boolean actualValue;

        GameCharacter testCharacter = new GameCharacter("Robert the over-confident", 1, 50, CharacterState.Attacking);
        testCharacter.pickUpItem(new Food("Apple", 0.2, 1,0, FoodState.Fresh, 182));
        testCharacter.setHealth(0);
        actualValue = testCharacter.eat(0);


        Assert.assertEquals(actualValue,expectedValue);
    }

    //Should depend on attack test
    @Test
    public void characterDead_Attack_NoChange() {

        String expectedValue = "";
        String actualValue;


        actualValue = defaultCharacter.attack(new GameCharacter("Goliath", 100, 50,CharacterState.Defending ));


        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_EquipWeapon_NoChange() {

        boolean expectedValue = false;
        boolean actualValue;

        expectedValue = false;
        GameCharacter testCharacter = new GameCharacter("Robert the over-confident", 1, 50, CharacterState.Attacking);
        testCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));
        testCharacter.setHealth(0);
        actualValue = defaultCharacter.equipWeapon(0);


        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_UnEquipWeapon_NoChange() {

        boolean expectedValue = false;
        boolean actualValue;

        GameCharacter testCharacter = new GameCharacter("Robert the over-confident", 1, 50, CharacterState.Attacking);
        testCharacter.pickUpItem(new Weapon("Sword", 5, 10,2,50,50,false));
        testCharacter.equipWeapon(0);
        testCharacter.setHealth(0);

        actualValue = testCharacter.unEquipWeapon(0);

        Assert.assertEquals(actualValue,expectedValue);

    }

    @Test
    public void characterDead_EquipArmour_NoChange() {
        boolean expectedValue = false;
        boolean actualValue;

        GameCharacter testCharacter = new GameCharacter("Robert the over-confident", 1, 50, CharacterState.Attacking);
        testCharacter.pickUpItem(new Armour("Chainmail", 15, 75, 5, 45, 65, false, ArmourTypes.Wearable, ArmourMaterial.Steel));
        testCharacter.setHealth(0);
        actualValue = defaultCharacter.equipArmour(0);


        Assert.assertEquals(actualValue,expectedValue);
    }


    @Test
    public void characterDead_Defend_NoChange() {
        CharacterState expectedValue = CharacterState.Dead;
        CharacterState actualValue;

        defaultCharacter.defend();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_Walk_NoChange() {

        CharacterState expectedValue = CharacterState.Dead;
        CharacterState actualValue;

        defaultCharacter.walk();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_Run_NoChange() {

        CharacterState expectedValue = CharacterState.Dead;
        CharacterState actualValue;

        defaultCharacter.run();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_Sleep_NoChange() {
        CharacterState expectedValue = CharacterState.Dead;
        CharacterState actualValue;

        defaultCharacter.sleep();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_Sleep_NoHealthChange() {
        int expectedValue = 0;
        int actualValue;

        defaultCharacter.sleep();
        actualValue = defaultCharacter.getHealth();

        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_WakeUp_NoChange() {
        CharacterState expectedValue = CharacterState.Dead;
        CharacterState actualValue;

        defaultCharacter.wakeUp();
        actualValue = defaultCharacter.getCharacterState();

        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_PickUpItem_NoChange() {

        double expectedValue;
        double actualValue;

        GameCharacter testCharacter = new GameCharacter("Robert the over-confident", 1, 50, CharacterState.Attacking);
        Armour testArmour = new Armour("Chainmail", 15, 75, 5, 45, 65, false, ArmourTypes.Wearable, ArmourMaterial.Steel);
        expectedValue = testCharacter.getTotalWeightOfItems();
        testCharacter.setHealth(0);
        testCharacter.pickUpItem(testArmour);
        actualValue = testCharacter.getTotalWeightOfItems();


        Assert.assertEquals(actualValue,expectedValue);
    }

    @Test
    public void characterDead_DropItem_NoChange() {
        double expectedValue;
        double actualValue;

        GameCharacter testCharacter = new GameCharacter("Robert the over-confident", 1, 50, CharacterState.Attacking);
        Armour testArmour = new Armour("Chainmail", 15, 75, 5, 45, 65, false, ArmourTypes.Wearable, ArmourMaterial.Steel);
        testCharacter.pickUpItem(testArmour);
        expectedValue = testCharacter.getTotalWeightOfItems();
        testCharacter.setHealth(0);
        defaultCharacter.dropItem(testArmour);
        actualValue = testCharacter.getTotalWeightOfItems();


        Assert.assertEquals(actualValue,expectedValue);
    }
}