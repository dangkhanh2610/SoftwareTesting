package vn.edu.softwaretesting.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import vn.edu.softwaretesting.core.WebTestBase;
import vn.edu.softwaretesting.pages.CartPage;
import vn.edu.softwaretesting.pages.CheckoutPage;
import vn.edu.softwaretesting.pages.InventoryPage;
import vn.edu.softwaretesting.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class CheckoutTest extends WebTestBase{
    private CheckoutPage checkoutPage;
    private CartPage cartPage;

    @BeforeEach
    void prepareCheckout() {
        LoginPage loginPage = new LoginPage(driver, baseUrl);
        LoginPage.LoginResult loginResult = loginPage.open().login("standard_user", "secret_sauce");
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
    @DisplayName("TC-CHK-01 - Checkout thành công")
    void testCheckoutSucces() {
        checkoutPage.fillInformation("Nguyen", "Van A", "700000");
        checkoutPage.continueToOverviewAndReadTotal();
        assertEquals("Item total: $29.99", checkoutPage.getItemTotal());
        assertEquals("Tax: $2.40", checkoutPage.getTax());
        assertEquals("Total: $32.39", checkoutPage.getTotal());
        assertEquals("Thank you for your order!", checkoutPage.finishOrder());
    }
    @Test
    @DisplayName("TC-CHK-02 - Bỏ trống trường FirstName")
    void testMissingFirstName() {
        checkoutPage.fillInformation("","Van A","700000");
        assertEquals("Error: First Name is required", checkoutPage.continueExpectingError());
    }
    @Test
    @DisplayName("TC-CHK-03 - Bỏ trống trường LastName")
    void testMissingLastName() {
        checkoutPage.fillInformation("Nguyen", "","700000");
        assertEquals("Error: Last Name is required", checkoutPage.continueExpectingError());
    }
    @Test
    @DisplayName("TC-CHK-04 - Bỏ trống trường Postal")
    void testMissingPostal() {
        checkoutPage.fillInformation("Nguyen", "Van A","");
        assertEquals("Error: Postal Code is required", checkoutPage.continueExpectingError());
    }
    @Test
    @DisplayName("TC-CHK-05 - Nhấn Cancel Checkout ")
    void testCancelCheckout() {
        checkoutPage.clickCancel();
        cartPage.open();
        assertTrue(driver.getCurrentUrl().contains("cart.html"), "Không quay lại giỏ hàng");
        assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"),"Sản phẩm  đã mất");
        assertEquals(1, cartPage.countProduct(), "Số lượng sản phẩm không đúng ");
    }
}