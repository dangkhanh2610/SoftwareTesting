package vn.edu.softwaretesting.core;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public final class DemoSlowMotion {

    private static final long CHAR_DELAY_MS = 50;
    private static final long STEP_DELAY_MS = 600;
    private static final long BEFORE_CLOSE_DELAY_MS = 2000;

    private DemoSlowMotion() {
    }

    public static boolean isDemoMode() {
        return !Boolean.parseBoolean(System.getProperty("headless", "true"));
    }

    public static void pause(WebDriver driver, long millis) {
        if (!isDemoMode() || driver == null || millis <= 0) {
            return;
        }
        new Actions(driver).pause(Duration.ofMillis(millis)).perform();
    }

    public static void pauseStep(WebDriver driver) {
        pause(driver, STEP_DELAY_MS);
    }

    public static void pauseBeforeClose(WebDriver driver) {
        pause(driver, BEFORE_CLOSE_DELAY_MS);
    }

    public static void typeSlowly(WebDriver driver, WebElement element, String text) {
        if (text == null || text.isEmpty()) {
            pause(driver, 350);
            return;
        }
        if (!isDemoMode()) {
            element.sendKeys(text);
            return;
        }
        element.click();
        for (int i = 0; i < text.length(); i++) {
            element.sendKeys(String.valueOf(text.charAt(i)));
            pause(driver, CHAR_DELAY_MS);
        }
        pause(driver, 250);
    }
}
