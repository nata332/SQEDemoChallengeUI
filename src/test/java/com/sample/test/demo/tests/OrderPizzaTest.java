package com.sample.test.demo.tests;

import com.sample.test.demo.BaseTest;
import com.sample.test.demo.constants.PizzaToppings;
import com.sample.test.demo.constants.PizzaTypes;
import com.sample.test.demo.pages.PizzaOrderPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

/**
 * @author Natallia_Rakitskaya@epam.com
 */
public class OrderPizzaTest extends BaseTest {
    private static final int MAX_PIZZAS_QTY = 99999;
    private static final String NAME = "Natallia";
    private static final String EMAIL = "abc@gmail.com";
    private static final String PHONE = "+12345678900";

    private static final String DEFAULT_PIZZA_TYPE_OPTION = "Choose Pizza";
    private static final String DEFAULT_TOPPING1_OPTION = "Choose a Toppings 1";
    private static final String DEFAULT_TOPPING2_OPTION = "Choose a Toppings 2";

    private static final String SUCCESS_MSG = "Thank you for your order! TOTAL:";
    private static final String MISSING_NAME_MSG = "Missing name";
    private static final String MISSING_PHONE_MSG = "Missing phone number";

    @Test(description = "Verify default option of Pizza Type drop-down")
    public void PizzaTypesDefaultOption() {
        String selectedOption = new PizzaOrderPage(driver).getPizzaTypeSelectedOption();
        Assert.assertEquals(selectedOption, DEFAULT_PIZZA_TYPE_OPTION, "Invalid default value!");
    }

    @Test(description = "Verify options of Pizza Type drop-down")
    public void PizzaTypesTest() {
        PizzaOrderPage page = new PizzaOrderPage(driver);
        verifyDropdownOptions(DEFAULT_PIZZA_TYPE_OPTION, page.getAllPizzaOptions(), PizzaTypes.getAllValues());
    }

    @Test(description = "Verify default option of the first Topping drop-down")
    public void FirstToppingDefaultOption() {
        String selectedOption = new PizzaOrderPage(driver).getTopping1SelectedOption();
        Assert.assertEquals(selectedOption, DEFAULT_TOPPING1_OPTION, "Invalid default value!");
    }

    @Test(description = "Verify options of the first Topping drop-down")
    public void FirstToppingOptionsTest() {
        PizzaOrderPage page = new PizzaOrderPage(driver);
        verifyDropdownOptions(DEFAULT_TOPPING1_OPTION, page.getAllTopping1Options(), PizzaToppings.getAllValues());
    }

    @Test(description = "Verify default option of the second Topping drop-down")
    public void SecondToppingDefaultOption() {
        String selectedOption = new PizzaOrderPage(driver).getTopping2SelectedOption();
        Assert.assertEquals(selectedOption, DEFAULT_TOPPING2_OPTION, "Invalid default value!");
    }

    @Test(description = "Verify options of the second Topping drop-down")
    public void SecondToppingOptionsTest() {
        PizzaOrderPage page = new PizzaOrderPage(driver);
        verifyDropdownOptions(DEFAULT_TOPPING2_OPTION, page.getAllTopping2Options(), PizzaToppings.getAllValues());
    }

    private void verifyDropdownOptions(String defaultValue, List<String> allDropdownOptions, List<String> expectedOptions) {
        allDropdownOptions.remove(defaultValue);
        Assert.assertEquals(allDropdownOptions.size(), expectedOptions.size(), "Unexpected number of options in dropdown!");
        Assert.assertTrue(allDropdownOptions.containsAll(expectedOptions), "Unexpected option found!");
    }

    @Test(description = "Verify Cost is calculated properly")
    public void verifyCostCalculation() {
        PizzaTypes pizzaType = PizzaTypes.getRandomPizzaType();
        int pizzasQuantity = generateRandomPizzasQuantity();
        PizzaOrderPage page = new PizzaOrderPage(driver)
                .specifyOrderDetails(pizzaType, PizzaToppings.getRandomTopping(), PizzaToppings.getRandomTopping(), pizzasQuantity);
        String orderTotal = calculateCost(pizzasQuantity, pizzaType.getCost());
        Assert.assertEquals(page.getCostValue(), orderTotal, "Incorrect cost is displayed!");
    }

    @Test(description = "Verify a happy path")
    public void happyPathTest() {
        PizzaTypes pizzaType = PizzaTypes.getRandomPizzaType();
        int pizzasQuantity = generateRandomPizzasQuantity();
        PizzaOrderPage page = new PizzaOrderPage(driver)
                .specifyOrderDetails(pizzaType, PizzaToppings.getRandomTopping(), PizzaToppings.getRandomTopping(), pizzasQuantity)
                .specifyContactInformation(NAME, EMAIL, PHONE)
                .chooseCardPaymentOption()
                .confirmOrder();
        String orderTotal = calculateCost(pizzasQuantity, pizzaType.getCost());
        String expectedMessage = new StringJoiner(" ").add(SUCCESS_MSG).add(orderTotal).add(pizzaType.getDisplayName()).toString();
        String actualMessage = page.retrievePopupMessage();
        Assert.assertEquals(actualMessage, expectedMessage, "Incorrect message shown!");
    }

    //@Test(description = "Verify Quantity outbound values + default")

    @DataProvider(name = "mandatoryFieldsChecker")
    public static Object[][] mandatoryFieldsValues() {
        return new Object[][]{
                {"", "", String.format("%s\n%s", MISSING_NAME_MSG, MISSING_PHONE_MSG)},
                {NAME, "", MISSING_PHONE_MSG},
                {"", PHONE, MISSING_NAME_MSG},
                {NAME, "    ", MISSING_PHONE_MSG},
                {"   ", PHONE, MISSING_NAME_MSG}};
    }

    //Defect found
    @Test(description = "Verify mandatory fields", dataProvider = "mandatoryFieldsChecker")
    public void mandatoryFieldsTest(String name, String phone, String expectedMessage) {
        PizzaOrderPage page = new PizzaOrderPage(driver)
                .specifyName(name)
                .specifyPhone(phone)
                .confirmOrder();
        Assert.assertEquals(page.retrievePopupMessage(), expectedMessage, "Incorrect message shown!");
    }

    //long names and phones
    //payment option
    //reset button

    /**
     * @return random number between 1 and MAX_PIZZAS_QTY
     */
    private int generateRandomPizzasQuantity() {
        return new Random().nextInt(MAX_PIZZAS_QTY) + 1;
    }

    private String calculateCost(int quantity, double cost) {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(locale);
        formatSymbols.setDecimalSeparator('.');
        return new DecimalFormat("0.##", formatSymbols).format(quantity * cost);
    }
}
