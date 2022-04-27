package com.lyit;

import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class ArmourTest {

    //Created a default constructor for use of setter tests
    Armour defaultArmour;
    @BeforeMethod(groups = {"setterTest","constructorTest"})
    public void initialiseDefaultArmour()
    {
        defaultArmour = new Armour("Black Tower Knight Armour", 10, 500,2,
                50, 95,true,ArmourTypes.Wearable,ArmourMaterial.Steel );
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

    @Test(groups = "constructorTest")
    public void armourConstructor_ValidConstructor_Passed() {

        //Just to check a valid constructor does not throw an exception
        Armour testArmour = new Armour("Black Tower Knight Armour", 10, 50,2,
                50, 95,true,ArmourTypes.Wearable,ArmourMaterial.Steel );

    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "constructorTest")
    public void armourConstructor_DefenceTooHigh_ExpectException() {

        Armour testArmour = new Armour("Black Tower Knight Armour", 10, 50,2,
                200, 95,true,ArmourTypes.Wearable,ArmourMaterial.Steel );
    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "constructorTest")
    public void armourConstructor_DefenceTooLow_ExpectException() {

        Armour testArmour = new Armour("Black Tower Knight Armour", 10, 50,2,
                0, 95,true,ArmourTypes.Wearable,ArmourMaterial.Steel );
    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "constructorTest")
    public void armourConstructor_HealthTooHigh_ExpectException() {

        Armour testArmour = new Armour("Black Tower Knight Armour", 10, 50,2,
                50, 150,true,ArmourTypes.Wearable,ArmourMaterial.Steel );
    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "constructorTest")
    public void armourConstructor_HealthTooLow_ExpectException() {

        Armour testArmour = new Armour("Black Tower Knight Armour", 10, 50,2,
                50, 0,true,ArmourTypes.Wearable,ArmourMaterial.Steel );
    }

    @Test(groups = "setterTest", dataProvider = "validRangeDataProvider")
    public void setArmourDefence_CorrectValue_Passed(int defence) {

        //Arrange

        defaultArmour.setArmourDefence(defence);
        int expectedResult = defence;
        int actualResult;

        //Act
        actualResult = defaultArmour.getArmourDefence();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);

    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest")
    public void setArmourDefence_SetOverLimit_ExpectException() {

        defaultArmour.setArmourDefence(150);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest")
    public void setArmourDefence_SetUnderLimit_ExpectException() {

        defaultArmour.setArmourDefence(0);
    }

    @Test(groups = "setterTest", dataProvider = "validRangeDataProvider")
    public void setArmourHealth_CorrectValue_Passed(int armourHealth) {

        //Arrange

        defaultArmour.setArmourHealth(armourHealth);
        int expectedResult = armourHealth;
        int actualResult;

        //Act
        actualResult = defaultArmour.getArmourHealth();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);

    }

    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest")
    public void setArmourHealth_SetOverLimit_ExpectException() {
        //Act
        defaultArmour.setArmourHealth(150);
    }
    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest")
    public void setArmourHealth_SetUnderLimit_ExpectException() {
        //Act
        defaultArmour.setArmourHealth(0);
    }


}