package vn.edu.softwaretesting.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotWatcher implements TestWatcher, AfterTestExecutionCallback {

    private static final Path SCREENSHOT_DIR = Paths.get("target", "screenshots");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Optional<Throwable> exception = context.getExecutionException();
        if (exception.isPresent()) {
            captureScreenshot(context, exception.get());
        }
    }

    private void captureScreenshot(ExtensionContext context, Throwable cause) {
        try {
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof WebTestBase webTestBase) {
                WebDriver driver = webTestBase.getDriver();
                if (driver instanceof TakesScreenshot takesScreenshot) {
                    Files.createDirectories(SCREENSHOT_DIR);
                    String className = context.getRequiredTestClass().getSimpleName();
                    String methodName = context.getRequiredTestMethod().getName();
                    String timestamp = LocalDateTime.now().format(FORMATTER);
                    String filename = String.format("%s_%s_%s.png", className, methodName, timestamp);
                    Path destination = SCREENSHOT_DIR.resolve(filename);

                    File screenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
                    Files.copy(screenshotFile.toPath(), destination);
                    System.out.printf("[TestWatcher] Ca kiểm thử FAIL: %s#%s. Đã tự động chụp ảnh màn hình lưu tại: %s%n",
                            className, methodName, destination.toAbsolutePath());
                }
            }
        } catch (Exception e) {
            System.err.println("[TestWatcher] Lỗi khi chụp ảnh màn hình: " + e.getMessage());
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.printf("[TestWatcher: FAIL] Ca kiểm thử '%s' không vượt qua: %s%n",
                context.getDisplayName(), cause != null ? cause.getMessage() : "Unknown");
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
    }
}
