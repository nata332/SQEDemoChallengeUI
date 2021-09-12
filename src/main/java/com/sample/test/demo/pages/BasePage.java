package com.sample.test.demo.pages;

import org.openqa.selenium.WebDriver;

/**
 * @author Natallia_Rakitskaya@epam.com
 */
public abstract class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
}
