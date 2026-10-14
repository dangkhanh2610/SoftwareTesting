
package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class InventoryPage {

    private final WebDriver driver;

    // Locators
    private final By inventoryItems =
            By.className("inventory_item");

    private final By productNames =
            By.className("inventory_item_name");

    private final By productPrices =
            By.className("inventory_item_price");

    private final By cartBadge =
            By.className("shopping_cart_badge");

    private final By sortDropdown =
            By.className("product_sort_container");

    private final By cartLink =
            By.className("shopping_cart_link");

    // Constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    // Tìm sản phẩm theo tên
    private WebElement findProduct(String productName) {
        for (WebElement item : driver.findElements(inventoryItems)) {
            String name = item.findElement(
                    By.className("inventory_item_name")).getText();

            if (name.equals(productName)) {
                return item;
            }
        }

        throw new IllegalArgumentException(
                "Không tìm thấy sản phẩm: " + productName);
    }

    // Thêm sản phẩm vào giỏ
    public void addProduct(String productName) {
        WebElement button = findProduct(productName).findElement(
                By.className("btn_inventory"));

        if (button.getText().equalsIgnoreCase("Add to cart")) {
            button.click();
        }
    }

    // Xóa sản phẩm khỏi giỏ
    public void removeProduct(String productName) {
        WebElement button = findProduct(productName).findElement(
                By.className("btn_inventory"));

        if (button.getText().equalsIgnoreCase("Remove")) {
            button.click();
        }
    }

    // Đọc số lượng badge; trả về 0 nếu badge không hiển thị
    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);

        if (badges.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(badges.get(0).getText());
    }

    // Kiểm tra giỏ hàng rỗng
    public boolean isCartEmpty() {
        return getCartBadgeCount() == 0;
    }

    // Kiểm tra sản phẩm đã được thêm vào giỏ
    public boolean isProductInCart(String productName) {
        WebElement button = findProduct(productName).findElement(
                By.className("btn_inventory"));

        return button.getText().equalsIgnoreCase("Remove");
    }

    // Các tùy chọn sắp xếp
    public enum SortOption {
        NAME_A_TO_Z("az"),
        NAME_Z_TO_A("za"),
        PRICE_LOW_TO_HIGH("lohi"),
        PRICE_HIGH_TO_LOW("hilo");

        private final String value;

        SortOption(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Sắp xếp sản phẩm
    public void sortBy(SortOption option) {
        Select select = new Select(
                driver.findElement(sortDropdown));

        select.selectByValue(option.getValue());
    }

    // Đọc danh sách tên sản phẩm
    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();

        for (WebElement element : driver.findElements(productNames)) {
            names.add(element.getText());
        }

        return names;
    }

    // Đọc danh sách giá sản phẩm
    public List<String> getProductPrices() {
        List<String> prices = new ArrayList<>();

        for (WebElement element : driver.findElements(productPrices)) {
            prices.add(element.getText());
        }

        return prices;
    }

    // Đọc tên và giá của toàn bộ sản phẩm
    public List<String> getProductList() {
        List<String> products = new ArrayList<>();

        List<WebElement> items = driver.findElements(inventoryItems);

        for (WebElement item : items) {
            String name = item.findElement(
                    By.className("inventory_item_name")).getText();

            String price = item.findElement(
                    By.className("inventory_item_price")).getText();

            products.add(name + " - " + price);
        }

        return products;
    }

    // Mở giỏ hàng
    public void openCart() {
        driver.findElement(cartLink).click();
    }
}
