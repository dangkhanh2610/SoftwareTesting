package vn.edu.softwaretesting.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import vn.edu.softwaretesting.core.WebTestBase;
import vn.edu.softwaretesting.pages.LoginPage;
import vn.edu.softwaretesting.pages.LoginPage.LoginResult;

@Tag("web")
@DisplayName("FR-01: Authentication (Login)")
class LoginTest extends WebTestBase {

    private LoginPage loginPage;

    @BeforeEach
    void openLoginPage() {
        loginPage = new LoginPage(driver, baseUrl).open();
    }

    
    @ParameterizedTest(name = "{0}: user=''{1}'', accepted={3}")
    @CsvFileSource(resources = "/login-data.csv", numLinesToSkip = 1)
    void loginEquivalencePartitioning(
            String testCaseId,
            String username,
            String password,
            boolean expectedAccepted,
            String expectedEvidence) {

        LoginResult actual = loginPage.login(username, password);

        assertEquals(expectedAccepted, actual.accepted(),
                () -> testCaseId + ": Kết quả accepted không khớp cho user: '" + username + "'");
        assertTrue(
                actual.currentUrl().contains(expectedEvidence)
                        || actual.message().contains(expectedEvidence),
                () -> testCaseId + ": Kỳ vọng chứa '" + expectedEvidence
                        + "', nhưng thực tế URL: '" + actual.currentUrl()
                        + "', thông báo: '" + actual.message() + "'");
    }

    
    @ParameterizedTest(name = "{0} [{1}]: user=''{2}'', pass=''{3}'' -> error/URL: {5}")
    @CsvSource({
        "TC-LOGIN-DT1, Quy tac R1, '', '', false, Username is required",
        "TC-LOGIN-DT2, Quy tac R2, standard_user, '', false, Password is required",
        "TC-LOGIN-DT3, Quy tac R3, locked_out_user, wrong_password, false, Username and password do not match",
        "TC-LOGIN-DT4, Quy tac R4, locked_out_user, secret_sauce, false, Sorry, this user has been locked out",
        "TC-LOGIN-DT5, Quy tac R5, standard_user, secret_sauce, true, inventory.html"
    })
    void loginDecisionTable(
            String testCaseId,
            String ruleName,
            String username,
            String password,
            boolean expectedAccepted,
            String expectedEvidence) {

        LoginResult actual = loginPage.login(username, password);

        assertEquals(expectedAccepted, actual.accepted(),
                () -> testCaseId + " (" + ruleName + "): Kết quả accepted không khớp");
        assertTrue(
                actual.currentUrl().contains(expectedEvidence)
                        || actual.message().contains(expectedEvidence),
                () -> testCaseId + " (" + ruleName + "): Kỳ vọng chứa '" + expectedEvidence
                        + "', nhưng thực tế URL: '" + actual.currentUrl()
                        + "', thông báo: '" + actual.message() + "'");
    }
}
