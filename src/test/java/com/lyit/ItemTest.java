package com.lyit;

import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class ItemTest {

    /*Armour, food and weapon are all child class of item therefore I did not consider it worthwhile to test the
    validity of the item functions in those child classes and only test in the parent class.*/

    //Created a default constructor for use of setter tests
    Item defaultItem ;
    @BeforeMethod(groups = {"setterTest","constructorTest"})
    public void initialiseDefaultItem()
    {
        defaultItem = new Item("Elixir Potion", 0.2, 125, 25);
    }

    @DataProvider(name = "itemNameFail")
    public Object[][]itemNameDataProviderFail()
    {
        return new Object[][]{
                {null},
                {""},
                {" "}
        };
    }

    @Test(dataProvider = "itemNameFail", expectedExceptions = IllegalArgumentException.class,groups = "setterTest",
    priority = 1)
    public void setItemName_EmptyValue_ExpectException(String itemName) {
        defaultItem.setItemName(itemName);
    }

    @Test(groups = "setterTest",dependsOnMethods = {"setItemName_EmptyValue_ExpectException"})
    public void setItemName_CorrectValue_Passed()
    {
        //Arrange

        defaultItem.setItemName("Phoenix Feather");
        String expectedResult = "Phoenix Feather";
        String actualResult;

        //Act
        actualResult = defaultItem.getItemName();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);
    }




    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest", priority = 2)
    public void setItemWeight_InvalidValue_ExpectException() {
        defaultItem.setItemWeight(0.0);
    }

    @Test(groups = "setterTest",dependsOnMethods = {"setItemWeight_InvalidValue_ExpectException"})
    public void setItemWeight_CorrectValue_Passed()
    {
        //Arrange

        defaultItem.setItemWeight(3.2);
        double expectedResult = 3.2;
        double actualResult;

        //Act
        actualResult = defaultItem.getItemWeight();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);
    }


    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest", priority = 3)
    public void setItemValue_InvalidValue_ExpectException() {
        defaultItem.setItemValue(-0.1);
    }

    @Test(groups = "setterTest",dependsOnMethods = {"setItemValue_InvalidValue_ExpectException"})
    public void setItemValue_CorrectValue_Passed()
    {
        //Arrange

        defaultItem.setItemValue(50);
        double expectedResult = 50;
        double actualResult;

        //Act
        actualResult = defaultItem.getItemValue();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);
    }



    @Test(expectedExceptions = IllegalArgumentException.class,groups = "setterTest", priority = 4)
    public void setItemMagicValue_InvalidValue_ExpectException() {
        defaultItem.setItemMagicValue(-1);
    }

    @Test(groups = "setterTest",dependsOnMethods = {"setItemValue_InvalidValue_ExpectException"})
    public void setItemMagicValue_CorrectValue_Passed()
    {
        //Arrange

        defaultItem.setItemValue(25);
        int expectedResult = 25;
        int actualResult;

        //Act
        actualResult = defaultItem.getItemMagicValue();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);
    }

    @DataProvider(name = "itemConstructorTestFail")
    public Object[][]itemConstructorDataProviderFail()
    {
        return new Object[][]{
                {"",1,1,0},//Empty Name
                {"Compass",0,1,0},//Weight must be above 0
                {"Compass",1,-0.1,0},//Value must be greater or equal to 0
                {"Compass",1,1,-1},//Magic value must be greater or equal to 0
        };
    }

    @Test(groups = "constructorTest", dataProvider = "itemConstructorTestFail",expectedExceptions = IllegalArgumentException.class)
    public void itemConstructor_InvalidData_ExpectException(String itemName, double itemWeight, double itemValue, int itemMagic){
        Item testFood = new Item(itemName, itemWeight, itemValue,itemMagic );
    }

    //To ensure no exceptions
    @Test(groups = "constructorTest")
    public void itemConstructor_ValidData_ExpectException(){
        Item testFood = new Item("Compass", 0.1, 0,0 );
    }





}