package vn.edu.softwaretesting.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By cartItems = By.cssSelector(".cart_item");
    private final By checkOutButton = By.id("checkout");
    private final By continueShopping = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public CartPage open() {
        wait.until(ExpectedConditions.urlContains("cart.html"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkOutButton));
        return this;
    }

    public List<String> readName() {
        List<String> result = new ArrayList<>();
        for (WebElement item : driver.findElements(cartItems)) {
            result.add(
                    item.findElement(
                            By.cssSelector(".inventory_item_name")
                    ).getText().trim()
            );
        }
        return result;
    }

    public List<Double> readPrice() {
        List<Double> result = new ArrayList<>();
        List<String> priceText = new ArrayList<>();
        for (WebElement item : driver.findElements(cartItems)) {
            priceText.add(
                    item.findElement(
                            By.cssSelector(".inventory_item_price")
                    ).getText().trim().replace("$", "")
            );
        }
        for (String price : priceText) {
            result.add(Double.parseDouble(price));
        }
        return result;
    }

    public boolean isProductInCart(String productName) {
        return readName().contains(productName);
    }

    public int countProduct() {
        return driver.findElements(cartItems).size();
    }

    public void clickCheckOut() {
        clickElement(checkOutButton);
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }

    public void removeProduct(String productName) {
        for (WebElement item : driver.findElements(cartItems)) {
            String name = item.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
            if (name.equals(productName)) {
                WebElement removeBtn = item.findElement(By.cssSelector("button"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", removeBtn);
                return;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy sản phẩm " + productName);
    }

    public void continueShopping() {
        clickElement(continueShopping);
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }
}
