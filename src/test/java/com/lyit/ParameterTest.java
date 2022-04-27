package com.lyit;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParameterTest {

    Weapon defaultWeapon = new Weapon("Ultima Sword", 5, 5000, 25,
            95,90, false);

    Item defaultItem = new Item("Elixir Potion", 0.2, 125, 25);

    //To ensure illegal argument exception is not called
    @Test(groups = "parameterTest")
    @Parameters({"testHitStrength"})
    public void setWeaponHitStrength_CorrectValue_Pass(int hitStrength) {
        defaultWeapon.setWeaponHitStrength(hitStrength);
        int actual = defaultWeapon.getWeaponHitStrength();
        Assert.assertEquals(actual,hitStrength);

    }

    //To ensure illegal argument exception is not called
    @Test(groups = "parameterTest")
    @Parameters({"testWeaponHealth"})
    public void setWeaponHealth_CorrectValue_Pass(int hitStrength) {
        defaultWeapon.setWeaponHitStrength(hitStrength);
        int actual = defaultWeapon.getWeaponHitStrength();
        Assert.assertEquals(actual,hitStrength);

    }

    //To ensure illegal argument exception is not called
    @Test(groups = "parameterTest")
    @Parameters({"testItemMagicValue"})
    public void setItemMagicValue_CorrectValue_Passed(int magicValue) {
        defaultItem.setItemMagicValue(magicValue);
        int actual = defaultItem.getItemMagicValue();
        Assert.assertEquals(actual,magicValue);

    }

    //To ensure illegal argument exception is not called
    @Test(groups = "parameterTest")
    @Parameters({"testItemName"})
    public void setItemName_CorrectValue_Passed(String testItemName) {
        defaultItem.setItemName(testItemName);
        String actual = defaultItem.getItemName();
        Assert.assertEquals(actual,testItemName);

    }

    //To ensure illegal argument exception is not called
    @Test(groups = "parameterTest")
    @Parameters({"testItemWeight"})
    public void setItemWeight_CorrectValue_Passed(double weight) {
        defaultItem.setItemWeight(weight);
        double actual = defaultItem.getItemWeight();
        Assert.assertEquals(actual,weight);

    }

    //To ensure illegal argument exception is not called
    @Test(groups = "parameterTest")
    @Parameters({"testItemValue"})
    public void setItemValue_CorrectValue_Passed(double value) {
        defaultItem.setItemValue(value);
        double actual = defaultItem.getItemValue();
        Assert.assertEquals(actual,value);

    }

}
