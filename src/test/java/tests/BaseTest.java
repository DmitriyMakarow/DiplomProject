package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.BasePage;
import pages.LoginPage;
import utils.PropertyReader;
import utils.TestListener;

import java.util.HashMap;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {

    protected WebDriver driver;

    protected final String baseUrl = "http://82.142.167.37:4881/";
    protected final String user = System.getProperty("user", PropertyReader.getProperty("user"));
    protected final String password = System.getProperty("password", PropertyReader.getProperty("password"));

    protected BasePage basePage;
    protected LoginPage loginPage;

    /**
     * Настройка браузера.
     * Поддерживаемые: CHROME, FIREFOX, EDGE.
     *
     * @param browser Имя браузера (параметр запуска)
     */
    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    @Description("Настройка браузера")
    public void setUp(@Optional("CHROME") String browser, ITestContext context) {

        Configuration.timeout = 10_000;
        Configuration.baseUrl = baseUrl;
        Configuration.clickViaJs = true;

        switch (browser.toUpperCase()) {
            case "CHROME":
                driver = createChromeDriver();
                break;
            case "FIREFOX":
                driver = createFirefoxDriver();
                break;
            case "EDGE":
                driver = createEdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unknown browser: " + browser);
        }

        context.setAttribute("driver", driver);

        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
    }

    private WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito", "--disable-notifications", "--disable-infobars");
        return new ChromeDriver(options);
    }

    private WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        return new FirefoxDriver(options);
    }

    private WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        return new EdgeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestContext ctx) {
        WebDriver drv = (WebDriver) ctx.getAttribute("driver");
        if (drv != null && !drv.toString().contains("(null)")) {
            drv.quit();
        }
    }
}