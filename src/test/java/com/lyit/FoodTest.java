package com.lyit;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FoodTest {


    //Created a default constructor for use of setter tests
    Food defaultFood;
    @BeforeMethod(groups = {"setterTest","constructorTest"})
    public void initialiseDefaultFood()
    {
        defaultFood = new Food("Elvish Bread", 0.35,6, 5, FoodState.Stale, 265);
    }

    @DataProvider(name = "constructorTestFail")
    public Object[][]constructorDataProviderFail()
    {
        return new Object[][]{
                {"Apple",0.182,2,0,FoodState.Mouldy,5080},
                {"Banana",0.98,3,0,FoodState.Fresh,-1524},
        };
    }

    @Test(groups = "constructorTest", dataProvider = "constructorTestFail",expectedExceptions = IllegalArgumentException.class)
    public void foodConstructor_CaloriesOutOfRange_ExpectException(String itemName, double itemWeight, double itemValue, int itemMagic, FoodState foodState, int calories) {

        Food testFood = new Food(itemName, itemWeight, itemValue,itemMagic,foodState,calories );
    }

    @DataProvider(name = "constructorTestPass")
    public Object[][]constructorDataProviderPass()
    {
        return new Object[][]{
                {"Apple",0.182,2,1,FoodState.Mouldy,58},
                {"Banana",0.98,4,1,FoodState.Fresh,92},
                {"Buffet",25,50,1,FoodState.Fresh,1000},
                {"Poison Apple",0.182,2,1,FoodState.Mouldy,-58},
                {"Poison Buffet",25,50,1,FoodState.Fresh,-1000},
        };
    }


    @Test(groups = "constructorTest", dataProvider = "constructorTestPass")
    public void foodConstructor_ValidConstructor_Passed(String itemName, double itemWeight, double itemValue, int itemMagic, FoodState foodState, int calories) {

        //Just to check a valid constructor does not throw an exception
        Food testFood = new Food(itemName, itemWeight, itemValue,itemMagic,foodState,calories );

    }
    @DataProvider(name = "calorieRangeFail")
    public Object[][]calorieDataProviderFail()
    {
        return new Object[][]{
                {-5000},
                {-1001},
                {1001},
                {5001},
        };
    }
    @Test(groups = "setterTest", dataProvider = "calorieRangeFail",expectedExceptions = IllegalArgumentException.class)
    public void setCalorie_OutsideRange_ExpectException(int calorie)
    {
        defaultFood.setCalories(calorie);
    }

    @DataProvider(name = "calorieRangePass")
    public Object[][]calorieDataProviderPass()
    {
        return new Object[][]{
                {-1000},
                {-50},
                {0},
                {50},
                {1000},
        };
    }

    @Test(groups = "setterTest", dataProvider = "calorieRangeFail",expectedExceptions = IllegalArgumentException.class)
    public void setCalorie_CorrectValue_Passed(int calorie)
    {
        //Arrange

        defaultFood.setCalories(calorie);
        int expectedResult = calorie;
        int actualResult;

        //Act
        actualResult = defaultFood.getCalories();

        //Assert
        Assert.assertEquals(actualResult,expectedResult);
    }

}