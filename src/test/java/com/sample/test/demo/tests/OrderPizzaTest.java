package com.sample.test.demo.tests;

import com.sample.test.demo.BaseTest;
import com.sample.test.demo.constants.PizzaToppings;
import com.sample.test.demo.constants.PizzaTypes;
import com.sample.test.demo.pages.PizzaOrderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.StringJoiner;

/**
 * @author Natallia_Rakitskaya@epam.com
 */
public class OrderPizzaTest extends BaseTest {
    public final String SUCCESS_TEXT = "Thank you for your order! TOTAL:";

    @Test(description = "Verify a happy path")
    public void happyPathTest() {
        PizzaTypes pizzaType = PizzaTypes.LARGE_TWOTOPPINGS;
        int pizzasQuantity = 10;
        PizzaOrderPage page = new PizzaOrderPage(driver)
                .specifyOrderDetails(pizzaType, PizzaToppings.PARMESAN, PizzaToppings.MANGOS, pizzasQuantity)
                .specifyContactInformation("Natallia", "abc@gmail.com", "+12345678900")
                .chooseCardPaymentOption()
                .confirmOrder();
        String orderTotal = new DecimalFormat("0.#").format(pizzasQuantity * pizzaType.getCost());
        String expectedMessage = new StringJoiner(" ")
                .add(SUCCESS_TEXT)
                .add(orderTotal)
                .add(pizzaType.getDisplayName())
                .toString();
        String actualMessage = page.retrievePopupMessage();
        Assert.assertEquals(actualMessage, expectedMessage, "Incorrect message shown!");
    }

}
