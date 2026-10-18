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
public class CheckoutErrorUserTest extends WebTestBase{
    private CheckoutPage checkoutPage;
    private CartPage cartPage;
    @BeforeEach
    void prepareCheckout() {
        LoginPage loginPage = new LoginPage(driver, baseUrl);
        LoginPage.LoginResult loginResult = loginPage.open().login("error_user", "secret_sauce");
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
    @DisplayName("TC-CHK-06 - Bỏ trống LastName")
    public void errorMisingLastName() {
        checkoutPage.fillInformation("Nguyen","","700000");
        checkoutPage.continueToOverviewAndReadTotal();
        assertEquals(true,driver.getCurrentUrl().contains("checkout-step-one.html"), "Hệ thống cho chuyển trang khi thiếu LastName");
        assertEquals("Error: Last Name is required",driver.findElement(By.cssSelector("[data-test='error']")).getText().trim(),"Hệ thống hiển thị lỗi yêu cầu nhập LastName ");
    }
}
