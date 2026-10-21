package vn.edu.softwaretesting.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE = By.id("continue");
    private static final By CANCEL = By.id("cancel");
    private static final By FINISH = By.id("finish");

    private static final By ERROR = By.cssSelector("[data-test='error']");
    private static final By ITEM_TOTAL = By.cssSelector(".summary_subtotal_label");
    private static final By TAX = By.cssSelector(".summary_tax_label");
    private static final By TOTAL = By.cssSelector(".summary_total_label");
    private static final By COMPLETE = By.cssSelector(".complete-header");
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public CheckoutPage fillInformation(String first, String last, String postal) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME)).sendKeys(first);
        driver.findElement(LAST_NAME).sendKeys(last);
        driver.findElement(POSTAL_CODE).sendKeys(postal);
        return this;
    }

    public String continueToOverviewAndReadTotal() {
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE)).click();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL)).getText();
    }

    public String getItemTotal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ITEM_TOTAL)).getText();
    }

    public String getTax() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TAX)).getText();
    }

    public String getTotal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL)).getText();
    }

    public String continueExpectingError() {
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE)).click();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR)).getText();
    }

    public String finishOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(FINISH)).click();
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(COMPLETE)).getText();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CANCEL)).click();
    }

}
