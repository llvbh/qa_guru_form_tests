package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserVersion = "147";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.pageLoadTimeout = 60000;
        Configuration.timeout = 10000;

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs",
        Map.of("profile.managed_default_content_settings.images", 2));
        Configuration.browserCapabilities = options;

    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
