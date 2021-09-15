package com.sample.test.demo;

import static org.testng.Assert.fail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.security.SecureRandom;
import java.util.Locale;

public class BaseTest {

    private Configuration config;
    protected WebDriver driver;
    protected String url;
    protected Locale locale;

    @BeforeClass(alwaysRun = true)
    public void init() throws Throwable {
        config = new Configuration();
        locale = new Locale.Builder().setRegion(config.getProperty("region")).build();
        url = config.getUrl();
        initializeDriver();
        navigateToSite();
    }

    protected void navigateToSite() {
        driver.get(url);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        try {
            driver.quit();

        } catch (Exception e) {
        }
    }

    private void initializeDriver() {
        if (config.getBrowser().equalsIgnoreCase("chrome")) {
            if (config.getPlatform().equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/mac/chromedriver");
            } else {
                System.setProperty("webdriver.chrome.driver",
                        "src/test/resources/chromedriver/windows/chromedriver.exe");
            }
            driver = new ChromeDriver();
        } else {
            fail("Unsupported browser " + config.getBrowser());
        }

    }

    public String generateRandomAlphaString(int length) {
        final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
        return generateRandomString(CHARACTERS, length);
    }

    public String generateRandomNumericString(int length) {
        final String NUMBERS = "0123456789";
        return generateRandomString(NUMBERS, length);
    }

    public String generateRandomString(String allowedCharactersSequence, int length) {
        SecureRandom randomizer = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            stringBuilder.append(allowedCharactersSequence.charAt(randomizer.nextInt(allowedCharactersSequence.length())));
        return stringBuilder.toString();
    }

}
