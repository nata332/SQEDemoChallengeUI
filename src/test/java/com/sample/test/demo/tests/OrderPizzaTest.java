package com.sample.test.demo.tests;

import com.sample.test.demo.BaseTest;
import com.sample.test.demo.constants.PizzaToppings;
import com.sample.test.demo.constants.PizzaTypes;
import com.sample.test.demo.pages.PizzaOrderPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.StringJoiner;

/**
 * @author Natallia_Rakitskaya@epam.com
 */
public class OrderPizzaTest extends BaseTest {
    private static final String NAME = "Natallia";
    private static final String EMAIL = "abc@gmail.com";
    private static final String PHONE = "+12345678900";
    private static final String SUCCESS_MSG = "Thank you for your order! TOTAL:";
    private static final String MISSING_NAME_MSG = "Missing name";
    private static final String MISSING_PHONE_MSG = "Missing phone number";

    @Test(description = "Verify a happy path")
    public void happyPathTest() {
        PizzaTypes pizzaType = PizzaTypes.LARGE_TWOTOPPINGS;
        int pizzasQuantity = 10;
        PizzaOrderPage page = new PizzaOrderPage(driver)
                .specifyOrderDetails(pizzaType, PizzaToppings.PARMESAN, PizzaToppings.MANGOS, pizzasQuantity)
                .specifyContactInformation(NAME, EMAIL, PHONE)
                .chooseCardPaymentOption()
                .confirmOrder();
        String orderTotal = new DecimalFormat("0.#").format(pizzasQuantity * pizzaType.getCost());
        String expectedMessage = new StringJoiner(" ").add(SUCCESS_MSG).add(orderTotal).add(pizzaType.getDisplayName()).toString();
        String actualMessage = page.retrievePopupMessage();
        Assert.assertEquals(actualMessage, expectedMessage, "Incorrect message shown!");
    }

    @DataProvider(name = "mandatoryFieldsChecker")
    public static Object[][] mandatoryFieldsValues() {
        return new Object[][]{{"", "", String.format("%s\n%s", MISSING_NAME_MSG, MISSING_PHONE_MSG)}, {NAME, "", MISSING_PHONE_MSG}, {"", PHONE, MISSING_NAME_MSG}};
    }

    @Test(description = "Verify mandatory fields", dataProvider = "mandatoryFieldsChecker")
    public void mandatoryFieldsTest(String name, String phone, String expectedMessage) {
        PizzaOrderPage page = new PizzaOrderPage(driver)
                .specifyName(name)
                .specifyPhone(phone)
                .confirmOrder();
        Assert.assertEquals(page.retrievePopupMessage(), expectedMessage, "Incorrect message shown!");
    }
}
