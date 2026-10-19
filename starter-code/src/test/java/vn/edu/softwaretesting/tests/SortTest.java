package vn.edu.softwaretesting.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import vn.edu.softwaretesting.core.WebTestBase;
import vn.edu.softwaretesting.pages.InventoryPage;
import vn.edu.softwaretesting.pages.LoginPage;
import vn.edu.softwaretesting.pages.LoginPage.LoginResult;

@Tag("web")
@DisplayName("FR-04: Sort")
public class SortTest extends WebTestBase{
    private InventoryPage loginInventoryPage(String username){
        LoginPage loginPage = new LoginPage(driver, baseUrl).open();
        LoginResult result = loginPage.login(username, "secret_sauce");
        assertTrue(result.accepted(), "Đăng nhập thất bại với tài khoản: " + username);
        return new InventoryPage(driver).open();
    }
    @ParameterizedTest(name = "{0}: user={1}, option={2}")
    @DisplayName("FR-04: Sắp xếp sản phẩm theo tên (A-Z, Z-A)")
    @CsvSource({
            "TC-SORT-EP1, standard_user, az, true",
            "TC-SORT-EP2, standard_user, za, false",
            "TC-SORT-EP1, problem_user,  az, true",
            "TC-SORT-EP1, visual_user,   az, true",
            "TC-SORT-EP2, visual_user,   za, false"
    })
    void sortByNamePass(String tcId, String username, String option, boolean ascending){
        InventoryPage inventoryPage = loginInventoryPage(username);

        List<String> actual = inventoryPage.sortBy(option).names();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(ascending ? Comparator.naturalOrder() : Comparator.reverseOrder() );
        assertEquals(expected, actual, () -> String.format("[%s] Thứ tự tên sản phẩm không đúng với user=%s, option=%s", tcId, username, option));
    }

    @ParameterizedTest(name = "{0}: user={1}, option={2}")
    @DisplayName("FR-04: Sắp xếp sản phẩm theo giá (low-high, high-low)")
    @CsvSource({
            "TC-SORT-EP3, standard_user, lohi, true",
            "TC-SORT-EP4, standard_user, hilo, false"
    })
    void sortByPricePass(String tcId, String username, String option, boolean ascending) {
        InventoryPage inventoryPage = loginInventoryPage(username);

        List<Double> actual = inventoryPage.sortBy(option).prices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(ascending ? Comparator.naturalOrder() : Comparator.reverseOrder());
        assertEquals(expected, actual, () -> String.format("[%s] Thứ tự giá sản phẩm không đúng với user=%s, option=%s", tcId, username, option));
    }

    @Tag("known-bug")
    @ParameterizedTest(name = "{0}: user={1}, option={2}")
    @DisplayName("FR-04: Lỗi sắp xếp theo tên Z-A với problem_user")
    @CsvSource({
            "TC-SORT-EP2[BUG-02], problem_user, za, false"
    })
    void sortByNameKnownBugs(String tcId, String username, String option, boolean ascending) {
        InventoryPage inventoryPage =loginInventoryPage(username);

        List<String> actual = inventoryPage.sortBy(option).names();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(ascending ? Comparator.naturalOrder() : Comparator.reverseOrder());
        assertEquals(expected, actual, () -> String.format("[%s] Hệ thống không sắp xếp tên theo %s cho %s", tcId, option, username));
    }

    @Tag("known-bug")
    @ParameterizedTest(name = "{0}: user={1}, option={2}")
    @DisplayName("FR-04: Lỗi sắp xếp theo giá với problem_user và visual_user")
    @CsvSource({
            "TC-SORT-EP3[BUG-02], problem_user, lohi, true",
            "TC-SORT-EP4[BUG-02], problem_user, hilo, false",
            "TC-SORT-EP3[BUG-06], visual_user,  lohi, true",
            "TC-SORT-EP4[BUG-06], visual_user,  hilo, false"
    })
    void sortByPriceKnownBugs(String tcId, String username, String option, boolean ascending) {
        InventoryPage inventoryPage = loginInventoryPage(username);

        List<Double> actual = inventoryPage.sortBy(option).prices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(ascending ? Comparator.naturalOrder() : Comparator.reverseOrder());
        assertEquals(expected, actual, () -> String.format("[%s] Hệ thống không sắp xếp giá theo %s cho %s", tcId, option, username));
    }

}


