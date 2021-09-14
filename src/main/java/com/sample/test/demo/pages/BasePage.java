package com.sample.test.demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Natallia_Rakitskaya@epam.com
 */
public abstract class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected List<String> getAllDropDownOptions(WebElement dropDown) {
        List<WebElement> options = new Select(dropDown).getOptions();
        List<String> optionsNames = new ArrayList<>();
        for (WebElement option : options) {
            optionsNames.add(option.getText());
        }
        return optionsNames;
    }
}
