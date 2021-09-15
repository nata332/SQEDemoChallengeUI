package com.sample.test.demo.tests;

import com.sample.test.demo.BaseTest;
import com.sample.test.demo.constants.PizzaToppings;
import com.sample.test.demo.constants.PizzaTypes;
import com.sample.test.demo.pages.PizzaOrderPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
    private static String NAME;
    private static String EMAIL;
    private static String PHONE;

    private static final int MAX_PIZZAS_QTY = 99999;

    private static final String DEFAULT_PIZZA_TYPE_OPTION = "Choose Pizza";
    private static final String DEFAULT_TOPPING1_OPTION = "Choose a Toppings 1";
    private static final String DEFAULT_TOPPING2_OPTION = "Choose a Toppings 2";

    private static final String SUCCESS_MSG = "Thank you for your order! TOTAL:";
    private static final String MISSING_NAME_MSG = "Missing name";
    private static final String MISSING_PHONE_MSG = "Missing phone number";

    @BeforeMethod
    private void setUp() {
        NAME = generateRandomAlphaString(100);
        EMAIL = String.format("%s@%s.com", generateRandomAlphaString(19), generateRandomAlphaString(76));
        PHONE = String.format("+%s", generateRandomNumericString(11));
    }

    @Test(description = "Verify default option of Pizza Type drop-down")
    public void pizzaTypesDefaultOptionTest() {
        navigateToSite();
        String selectedOption = new PizzaOrderPage(driver).getPizzaTypeSelectedOption();
        Assert.assertEquals(selectedOption, DEFAULT_PIZZA_TYPE_OPTION, "Invalid default value!");
    }

    @Test(description = "Verify options of Pizza Type drop-down")
    public void pizzaTypesTest() {
        PizzaOrderPage page = new PizzaOrderPage(driver);
        verifyDropdownOptions(DEFAULT_PIZZA_TYPE_OPTION, page.getAllPizzaOptions(), PizzaTypes.getAllValues());
    }

    @Test(description = "Verify default option of the first Topping drop-down")
    public void firstToppingDefaultOptionTest() {
        navigateToSite();
        String selectedOption = new PizzaOrderPage(driver).getTopping1SelectedOption();
        Assert.assertEquals(selectedOption, DEFAULT_TOPPING1_OPTION, "Invalid default value!");
    }

    @Test(description = "Verify options of the first Topping drop-down")
    public void firstToppingOptionsTest() {
        PizzaOrderPage page = new PizzaOrderPage(driver);
        verifyDropdownOptions(DEFAULT_TOPPING1_OPTION, page.getAllTopping1Options(), PizzaToppings.getAllValues());
    }

    @Test(description = "Verify default option of the second Topping drop-down")
    public void secondToppingDefaultOptionTest() {
        navigateToSite();
        String selectedOption = new PizzaOrderPage(driver).getTopping2SelectedOption();
        Assert.assertEquals(selectedOption, DEFAULT_TOPPING2_OPTION, "Invalid default value!");
    }

    @Test(description = "Verify options of the second Topping drop-down")
    public void secondToppingOptionsTest() {
        PizzaOrderPage page = new PizzaOrderPage(driver);
        verifyDropdownOptions(DEFAULT_TOPPING2_OPTION, page.getAllTopping2Options(), PizzaToppings.getAllValues());
    }

    private void verifyDropdownOptions(String defaultValue, List<String> allDropdownOptions, List<String> expectedOptions) {
        allDropdownOptions.remove(defaultValue);
        Assert.assertEquals(allDropdownOptions.size(), expectedOptions.size(), "Unexpected number of options in dropdown!");
        Assert.assertTrue(allDropdownOptions.containsAll(expectedOptions), "Unexpected option found!");
    }

    @Test(description = "Verify Cost is calculated properly")
    public void costCalculationTest() {
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

    @DataProvider(name = "mandatoryFieldsChecker")
    public static Object[][] mandatoryFieldsValues() {
        return new Object[][]{
                {"", "", String.format("%s\n%s", MISSING_NAME_MSG, MISSING_PHONE_MSG)},
                {NAME, "", MISSING_PHONE_MSG},
                {"", PHONE, MISSING_NAME_MSG},
                {NAME, "    ", MISSING_PHONE_MSG},
                {"   ", PHONE, MISSING_NAME_MSG}};
    }

    //Defect #4
    @Test(description = "Verify mandatory fields", dataProvider = "mandatoryFieldsChecker")
    public void mandatoryFieldsTest(String name, String phone, String expectedMessage) {
        PizzaOrderPage page = new PizzaOrderPage(driver)
                .specifyName(name)
                .specifyPhone(phone)
                .confirmOrder();
        Assert.assertEquals(page.retrievePopupMessage(), expectedMessage, "Incorrect message shown!");
    }

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

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        PizzaOrderPage page = new PizzaOrderPage(driver);
        if (page.isPopupMessageDisplayed()) {
            page.closePopupWindow();
        }
    }
}
