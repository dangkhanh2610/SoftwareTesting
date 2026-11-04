package vn.edu.softwaretesting.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    private void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public CheckoutPage fillInformation(String first, String last, String postal) {
        WebElement firstInput = wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME));
        firstInput.clear();
        if (first != null && !first.isEmpty()) {
            firstInput.sendKeys(first);
        }

        WebElement lastInput = wait.until(ExpectedConditions.visibilityOfElementLocated(LAST_NAME));
        lastInput.clear();
        if (last != null && !last.isEmpty()) {
            lastInput.sendKeys(last);
        }

        WebElement postalInput = wait.until(ExpectedConditions.visibilityOfElementLocated(POSTAL_CODE));
        postalInput.clear();
        if (postal != null && !postal.isEmpty()) {
            postalInput.sendKeys(postal);
        }
        return this;
    }

    public String continueToOverviewAndReadTotal() {
        clickElement(CONTINUE);
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
        clickElement(CONTINUE);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR)).getText();
    }

    public String finishOrder() {
        clickElement(FINISH);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(COMPLETE)).getText();
    }

    public void clickCancel() {
        clickElement(CANCEL);
    }
}