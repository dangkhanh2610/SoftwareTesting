
package tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.InventoryPage;

class InventoryPageTest {

    private WebDriver driver;
    private InventoryPage inventory;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://sauce-demo.myshopify.com/");

        // Đăng nhập tài khoản demo
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Chờ trang Inventory tải xong
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_list")));

        inventory = new InventoryPage(driver);
    }

    @Test
    void testAddProductAndBadge() {
        inventory.addProduct("Sauce Labs Backpack");

        assertEquals(1, inventory.getCartBadgeCount());
        assertTrue(inventory.isProductInCart("Sauce Labs Backpack"));
    }

    @Test
    void testRemoveProductAndEmptyCart() {
        inventory.addProduct("Sauce Labs Backpack");
        inventory.removeProduct("Sauce Labs Backpack");

        assertTrue(inventory.isCartEmpty());
    }

    @Test
    void testSortProductsByPrice() {
        inventory.sortBy(InventoryPage.SortOption.PRICE_LOW_TO_HIGH);

        assertEquals(
                inventory.getProductPrices().stream()
                        .map(price -> Double.parseDouble(
                                price.replace("$", "")))
                        .sorted()
                        .toList(),
                inventory.getProductPrices().stream()
                        .map(price -> Double.parseDouble(
                                price.replace("$", "")))
                        .toList()
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
