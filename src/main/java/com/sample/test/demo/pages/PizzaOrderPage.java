package com.sample.test.demo.pages;

import com.sample.test.demo.constants.PizzaToppings;
import com.sample.test.demo.constants.PizzaTypes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * @author Natallia_Rakitskaya@epam.com
 */
public class PizzaOrderPage extends BasePage {
    private static final Logger LOGGER = LogManager
            .getLogger(PizzaOrderPage.class);

    @FindBy(id = "pizza1Pizza")
    private WebElement pizzaTypeDropDown;

    @FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings1']")
    private WebElement firstToppingDropDown;

    @FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings2']")
    private WebElement secondToppingDropDown;

    @FindBy(id = "pizza1Qty")
    private WebElement quantityInputField;

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

    @FindBy(css = "[title=Close]")
    private WebElement closeButton;

    private By costFieldLocator = By.id("pizza1Cost");

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
        new Select(pizzaTypeDropDown).selectByVisibleText(String.format("%s $%s", pizzaType.getDisplayName(), pizzaType.getFormattedCost()));
        return this;
    }

    public String getPizzaTypeSelectedOption() {
        return new Select(pizzaTypeDropDown).getFirstSelectedOption().getText();
    }

    public List<String> getAllPizzaOptions() {
        return getAllDropDownOptions(pizzaTypeDropDown);
    }

    public PizzaOrderPage selectFirstTopping(PizzaToppings topping) {
        new Select(firstToppingDropDown).selectByVisibleText(topping.getDisplayName());
        return this;
    }

    public String getTopping1SelectedOption() {
        return new Select(firstToppingDropDown).getFirstSelectedOption().getText();
    }

    public List<String> getAllTopping1Options() {
        return getAllDropDownOptions(firstToppingDropDown);
    }

    public PizzaOrderPage selectSecondTopping(PizzaToppings topping) {
        new Select(secondToppingDropDown).selectByVisibleText(topping.getDisplayName());
        return this;
    }

    public String getTopping2SelectedOption() {
        return new Select(secondToppingDropDown).getFirstSelectedOption().getText();
    }

    public List<String> getAllTopping2Options() {
        return getAllDropDownOptions(secondToppingDropDown);
    }

    public PizzaOrderPage specifyPizzasQuantity(int pizzasQuantity) {
        quantityInputField.clear();
        quantityInputField.sendKeys(String.valueOf(pizzasQuantity));
        quantityInputField.sendKeys(Keys.TAB);
        return this;
    }

    public String getCostValue() {
        WebElement costField = driver.findElement(costFieldLocator);
        return costField.getAttribute("value");
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

    public boolean isPopupMessageDisplayed() {
        return popupMessage.isDisplayed();
    }

    public void closePopupWindow() {
        closeButton.click();
    }
}
