package com.lyit;

import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class WeaponTest {


    //Created a default constructor for use of setter tests
    Weapon defaultWeapon;
    @BeforeMethod(groups = {"setterTest","constructorTest"})
    public void initialiseDefaultWeapon()
    {
        defaultWeapon = new Weapon("Ultima Sword", 5, 5000, 25,
                95,90, false);
    }

    //Wanted to check numbers between 1 and 100 are valid and importantly that both 1 and 100 are still consider in
    //range.
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
    public void setWeaponHealth_CorrectValue_Passed(int health) {

        //Arrange

        defaultWeapon.setWeaponHealth(health);
        int expectedResult = health;
        int actualResult;

        //Act
        actualResult = defaultWeapon.getWeaponHealth();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);

    }

    @Test(groups = "setterTest", dataProvider = "validRangeDataProvider")
    public void setWeaponHitStrength_CorrectValue_Passed(int hitStregth) {

        //Arrange

        defaultWeapon.setWeaponHitStrength(hitStregth);
        int expectedResult = hitStregth;
        int actualResult;

        //Act
        actualResult = defaultWeapon.getWeaponHitStrength();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);

    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest")
    public void setWeaponHitStrength_OverLimit_ExpectException() {
        defaultWeapon.setWeaponHitStrength(101);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest")
    public void setWeaponHitStrength_UnderLimit_ExpectException() {
        defaultWeapon.setWeaponHitStrength(0);
    }



    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest")
    public void setWeaponHealth_OverLimit_ExpectException() {
        defaultWeapon.setWeaponHitStrength(101);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest")
    public void setWeaponHealth_UnderLimit_ExpectException() {
        defaultWeapon.setWeaponHitStrength(0);
    }



    @DataProvider(name = "weaponConstructorTestFail")
    public Object[][]constructorDataProviderFail()
    {
        return new Object[][]{
                {"Miracle Knife",0.5,25,0,65,101,false},//Weapon health over 100
                {"Broken Knife",0.5,25,0,5,0,false},//Weapon health below 1
                {"Pillow",0.1,5,0,0,5,false},//Hit strength below 1
                {"Cannon",0.5,25,0,101,75,false},//Hit strength above 100
        };
    }

    @Test(groups = "constructorTest", dataProvider = "weaponConstructorTestFail",expectedExceptions = IllegalArgumentException.class)
    public void weaponConstructor_InvalidData_ExpectException(String itemName, double itemWeight, double itemValue, int itemMagic, int weaponHitStrength, int weaponHealth, boolean doubleHanded)
    {
        Weapon defaultWeapon = new Weapon(itemName,itemWeight,itemValue,itemMagic,weaponHitStrength,weaponHealth,doubleHanded);
    }

    @DataProvider(name = "weaponConstructorTestPass")
    public Object[][]constructorDataProviderPass()
    {
        return new Object[][]{
                {"Rusty Knife",0.5,25,0,1,1,false},
                {"Sun Spear",5.5,600,100,100,100,true},
        };
    }

    @Test(groups = "constructorTest", dataProvider = "weaponConstructorTestPass")
    public void weaponConstructor_ValidData_Passed(String itemName, double itemWeight, double itemValue, int itemMagic, int weaponHitStrength, int weaponHealth, boolean doubleHanded)
    {
        Weapon defaultWeapon = new Weapon(itemName,itemWeight,itemValue,itemMagic,weaponHitStrength,weaponHealth,doubleHanded);
    }
}