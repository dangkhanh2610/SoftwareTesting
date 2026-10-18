package vn.edu.softwaretesting.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By inventoryList =
            By.cssSelector(".inventory_list");

    private final By productItems =
            By.cssSelector(".inventory_item");

    private final By productNames =
            By.cssSelector(".inventory_item_name");

    private final By productPrices =
            By.cssSelector(".inventory_item_price");

    private final By cartBadge =
            By.cssSelector(".shopping_cart_badge");

    private final By cartLink =
            By.cssSelector(".shopping_cart_link");

    private final By sortDropdown =
            By.cssSelector("select.product_sort_container");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));
    }

    // Chờ trang sản phẩm hiển thị
    public InventoryPage open() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        inventoryList));
        return this;
    }

    // Thêm sản phẩm vào giỏ hàng
    // Ví dụ: addToCart("sauce-labs-backpack")
    public InventoryPage addToCart(String productId) {
        By addButton = By.id("add-to-cart-" + productId);

        wait.until(
                        ExpectedConditions.elementToBeClickable(addButton))
                .click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("remove-" + productId)));

        return this;
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public InventoryPage removeFromCart(String productId) {
        By removeButton = By.id("remove-" + productId);

        wait.until(
                        ExpectedConditions.elementToBeClickable(removeButton))
                .click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("add-to-cart-" + productId)));

        return this;
    }

    // Đọc số lượng sản phẩm trên badge giỏ hàng
    // Nếu giỏ trống, badge không xuất hiện và trả về 0
    public int cartBadgeCount() {
        List<WebElement> badges =
                driver.findElements(cartBadge);

        if (badges.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(
                badges.get(0).getText().trim());
    }

    // Sắp xếp sản phẩm
    // az: Tên A đến Z
    // za: Tên Z đến A
    // lohi: Giá thấp đến cao
    // hilo: Giá cao đến thấp
    public InventoryPage sortBy(String option) {
        if (!List.of("az", "za", "lohi", "hilo")
                .contains(option)) {
            throw new IllegalArgumentException(
                    "Sort option không hợp lệ: " + option);
        }

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        sortDropdown));

        new Select(dropdown).selectByValue(option);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        inventoryList));

        return this;
    }

    // Đọc danh sách tên sản phẩm
    public List<String> names() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productNames));

        List<String> result = new ArrayList<>();

        for (WebElement element :
                driver.findElements(productNames)) {
            result.add(element.getText().trim());
        }

        return result;
    }

    // Đọc danh sách giá sản phẩm
    public List<Double> prices() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productPrices));

        List<Double> result = new ArrayList<>();

        for (WebElement element :
                driver.findElements(productPrices)) {

            String price = element.getText()
                    .trim()
                    .replace("$", "");

            result.add(Double.parseDouble(price));
        }

        return result;
    }

    // Đọc danh sách sản phẩm gồm tên và giá
    public List<Product> products() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        inventoryList));

        List<WebElement> items =
                driver.findElements(productItems);

        List<Product> result = new ArrayList<>();

        for (WebElement item : items) {
            String name = item.findElement(
                            By.cssSelector(".inventory_item_name"))
                    .getText().trim();

            String priceText = item.findElement(
                            By.cssSelector(".inventory_item_price"))
                    .getText().trim()
                    .replace("$", "");

            double price = Double.parseDouble(priceText);

            result.add(new Product(name, price));
        }

        return result;
    }

    // Mở trang giỏ hàng
    public void openCart() {
        wait.until(
                        ExpectedConditions.elementToBeClickable(cartLink))
                .click();
    }

    // Đối tượng lưu tên và giá sản phẩm
    public record Product(String name, double price) {
    }
}