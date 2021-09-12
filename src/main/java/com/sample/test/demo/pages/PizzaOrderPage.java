package com.sample.test.demo.pages;

import com.sample.test.demo.constants.PizzaToppings;
import com.sample.test.demo.constants.PizzaTypes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Natallia_Rakitskaya@epam.com
 */
public class PizzaOrderPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PizzaOrderPage.class);

    @FindBy(id = "pizza1Pizza")
    private WebElement pizzaTypeList;

    @FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings1']")
    private WebElement firstToppingList;

    @FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings2']")
    private WebElement secondToppingList;

    @FindBy(id = "pizza1Qty")
    private WebElement quantityInputField;

    @FindBy(id = "pizza1Cost")
    private WebElement costField;

    @FindBy(id = "name")
    private WebElement nameInputField;

    @FindBy(id = "email")
    private WebElement emailInputField;

    @FindBy(id = "phone")
    private WebElement phoneInputField;

    @FindBy(id = "ccpayment")
    private WebElement cardPaymentOptionButton;

    @FindBy(id = "cashpayment")
    private WebElement cashPaymentOptionButton;

    @FindBy(id = "placeOrder")
    private WebElement placeOrderButton;

    @FindBy(id = "reset")
    private WebElement resetButton;

    @FindBy(xpath = "//div[@id='dialog']/p")
    private WebElement popupMessage;

    public PizzaOrderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public PizzaOrderPage specifyOrderDetails(PizzaTypes pizzaType, PizzaToppings firstTopping, PizzaToppings secondTopping, int pizzasQuantity) {
        selectPizzaType(pizzaType);
        selectFirstTopping(firstTopping);
        selectSecondTopping(secondTopping);
        specifyPizzasQuantity(pizzasQuantity);
        LOGGER.info(String.format("Order details specified: %s with %s and %s, quantity: %s",
                pizzaType.getDisplayName(), firstTopping.getDisplayName(), secondTopping.getDisplayName(), pizzasQuantity));
        return this;
    }

    public PizzaOrderPage selectPizzaType(PizzaTypes pizzaType) {
        new Select(pizzaTypeList).selectByVisibleText(String.format("%s $%s", pizzaType.getDisplayName(), pizzaType.getFormattedCost()));
        return this;
    }

    public PizzaOrderPage selectFirstTopping(PizzaToppings topping) {
        new Select(firstToppingList).selectByVisibleText(topping.getDisplayName());
        return this;
    }

    public PizzaOrderPage selectSecondTopping(PizzaToppings topping) {
        new Select(secondToppingList).selectByVisibleText(topping.getDisplayName());
        return this;
    }

    public PizzaOrderPage specifyPizzasQuantity(int pizzasQuantity) {
        quantityInputField.clear();
        quantityInputField.sendKeys(String.valueOf(pizzasQuantity));
        return this;
    }

    public PizzaOrderPage specifyContactInformation(String name, String email, String phone) {
        specifyName(name);
        specifyEmail(email);
        specifyPhone(phone);
        return this;
    }

    public PizzaOrderPage specifyName(String name) {
        nameInputField.clear();
        nameInputField.sendKeys(name);
        return this;
    }

    public PizzaOrderPage specifyEmail(String email) {
        emailInputField.clear();
        emailInputField.sendKeys(email);
        return this;
    }

    public PizzaOrderPage specifyPhone(String phone) {
        phoneInputField.clear();
        phoneInputField.sendKeys(phone);
        return this;
    }

    public PizzaOrderPage chooseCardPaymentOption() {
        cardPaymentOptionButton.click();
        return this;
    }

    public PizzaOrderPage chooseCashPaymentOption() {
        cashPaymentOptionButton.click();
        return this;
    }

    public PizzaOrderPage confirmOrder() {
        placeOrderButton.click();
        return this;
    }

    public PizzaOrderPage resetOrderDetails() {
        resetButton.click();
        return this;
    }

    public String retrievePopupMessage() {
        return popupMessage.getText();
    }
}
