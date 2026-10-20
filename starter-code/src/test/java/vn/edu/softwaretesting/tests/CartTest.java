
package vn.edu.softwaretesting.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import vn.edu.softwaretesting.core.WebTestBase;
import vn.edu.softwaretesting.pages.InventoryPage;
import vn.edu.softwaretesting.pages.LoginPage;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("FR-02: Kiểm thử giá trị biên của giỏ hàng")
public class CartTest extends WebTestBase {

    private InventoryPage inventory;

    private static final String[] PRODUCTS = {
        "sauce-labs-backpack",
        "sauce-labs-bike-light",
        "sauce-labs-bolt-t-shirt",
        "sauce-labs-fleece-jacket",
        "sauce-labs-onesie",
        "test.allthethings()-t-shirt-(red)"
    };

    @BeforeEach
    void setUpCartTest() {
        new LoginPage(driver, baseUrl)
                .open()
                .login("standard_user", "secret_sauce");

        inventory = new InventoryPage(driver).open();
    }

    @Test
    @DisplayName("TC-CART-B1: Giỏ rỗng không hiển thị badge")
    void emptyCartHasNoBadge() {
        assertEquals(
                0,
                inventory.cartBadgeCount(),
                "Badge phải không xuất hiện khi giỏ hàng rỗng"
        );
    }

    @Test
    @DisplayName("TC-CART-B2: Thêm 1 sản phẩm, badge bằng 1")
    void addOneProduct() {
        inventory.addToCart(PRODUCTS[0]);

        assertEquals(
                1,
                inventory.cartBadgeCount(),
                "Badge phải hiển thị số 1"
        );
    }

    @Test
    @DisplayName("TC-CART-B3: Giỏ có 5 sản phẩm, badge bằng 5")
    void cartHasFiveProducts() {
        for (String product : PRODUCTS) {
            inventory.addToCart(product);
        }

        inventory.removeFromCart(PRODUCTS[5]);

        assertEquals(
                5,
                inventory.cartBadgeCount(),
                "Badge phải hiển thị số 5"
        );
    }

    @Test
    @DisplayName("TC-CART-B4: Thêm đủ 6 sản phẩm, badge bằng 6")
    void cartHasSixProducts() {
        for (String product : PRODUCTS) {
            inventory.addToCart(product);
        }

        assertEquals(
                6,
                inventory.cartBadgeCount(),
                "Badge phải hiển thị số 6"
        );
    }

    @Test
    @DisplayName("TC-CART-B5: Xóa sản phẩm cuối cùng, badge biến mất")
    void removeLastProduct() {
        inventory.addToCart(PRODUCTS[0]);

        assertEquals(
                1,
                inventory.cartBadgeCount(),
                "Trước khi xóa, badge phải bằng 1"
        );

        inventory.removeFromCart(PRODUCTS[0]);

        assertEquals(
                0,
                inventory.cartBadgeCount(),
                "Badge phải biến mất khi giỏ hàng trở về 0"
        );
    }
}
