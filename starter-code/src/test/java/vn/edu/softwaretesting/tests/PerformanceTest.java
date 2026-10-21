
package vn.edu.softwaretesting.tests;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import vn.edu.softwaretesting.core.WebTestBase;
import vn.edu.softwaretesting.pages.LoginPage;

class PerformanceTest extends WebTestBase {

    @Test
    @Tag("known-bug")
    @DisplayName("TC-NFR-01: Login performance")
    void inventoryLoadsWithin3Seconds() {

        LoginPage loginPage = new LoginPage(driver, baseUrl);

        loginPage.open();

        long start = System.nanoTime();

        LoginPage.LoginResult result = loginPage.login(
                "performance_glitch_user",
                "secret_sauce"
        );

        long elapsedMs = Duration.ofNanos(
                System.nanoTime() - start
        ).toMillis();

        System.out.println("Thời gian đăng nhập: "
                + elapsedMs + " ms");

        System.out.println("Kết quả: "
                + result.message());

        assertTrue(
                result.accepted(),
                "Đăng nhập không thành công"
        );

        assertTrue(
                elapsedMs <= 3000,
                "Vượt ngưỡng 3000 ms: " + elapsedMs + " ms"
        );
    }
}
