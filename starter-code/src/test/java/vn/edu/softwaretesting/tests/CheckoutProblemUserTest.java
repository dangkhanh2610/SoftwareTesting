package vn.edu.softwaretesting.tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import vn.edu.softwaretesting.core.WebTestBase;
import vn.edu.softwaretesting.pages.CartPage;
import vn.edu.softwaretesting.pages.CheckoutPage;
import vn.edu.softwaretesting.pages.InventoryPage;
import vn.edu.softwaretesting.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutProblemUserTest extends WebTestBase{
    private CheckoutPage checkoutPage;
    private CartPage cartPage;
    @BeforeEach
    void prepareCheckout() {
        LoginPage loginPage = new LoginPage(driver, baseUrl);
        LoginPage.LoginResult loginResult = loginPage.open().login("problem_user", "secret_sauce");
        assertTrue(loginResult.accepted(), "Đăng nhập thất bại:" + loginResult.message());

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.open().addToCart("sauce-labs-backpack");
        inventoryPage.openCart();
        cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.clickCheckOut();
        checkoutPage = new CheckoutPage(driver);
    }
    @Test
    @DisplayName("TC-CHK-07 - Lỗi nhập liệu ở LastName thì hiện thị ở FirstName ")
    public void errorChangeName() {
        checkoutPage.fillInformation("Nguyen","Van A", "700000");
        assertEquals("Nguyen",driver.findElement(By.id("first-name")).getAttribute("value"), "Lỗi: FirstName bị thay đổi khi nhập LastName ");
        assertEquals("Nguyen",driver.findElement(By.id("last-name")).getAttribute("value")," Lỗi LastName không giữ đúng giá trị khi nhập liệu");
    }
}
