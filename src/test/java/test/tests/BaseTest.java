package test.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;
import test.pages.base.BasePage;
import test.pages.LoginPage;
import test.pages.base.BaseSteps;
import utils.PropertyReader;
import utils.TestListener;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.open;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {

    protected final String baseUrl = "http://82.142.167.37:4881/#";
    protected final String user = System.getProperty("user", PropertyReader.getProperty("user"));
    protected final String password = System.getProperty("password", PropertyReader.getProperty("password"));

    protected BasePage basePage;
    protected LoginPage loginPage;
    protected BaseSteps baseSteps;

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
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.screenshots = true;
        Configuration.reportsFolder = "build/reports/tests";
        Configuration.savePageSource = true;
        Configuration.baseUrl = baseUrl;
        Configuration.timeout = 10000;
        Configuration.clickViaJs = true;
        Configuration.headless = false;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-infobars");

        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        switch (browser.toUpperCase()) {
            case "CHROME":
                Configuration.browser = "chrome";
                Configuration.browserCapabilities = chromeOptions;
                break;
            case "FIREFOX":
                Configuration.browser = "firefox";
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-private");
                Configuration.browserCapabilities = firefoxOptions;
                break;
            case "EDGE":
                Configuration.browser = "edge";
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("-inprivate");
                edgeOptions.addArguments("--disable-notifications");
                Configuration.browserCapabilities = edgeOptions;
                break;
            default:
                throw new IllegalArgumentException("Unknown browser: " + browser);
        }

        open(baseUrl);

        context.setAttribute("driver", WebDriverRunner.getWebDriver());

        basePage = new BasePage();
        loginPage = new LoginPage();
        baseSteps = new BaseSteps();
    }

    @AfterMethod(alwaysRun = true)
    @Description("Закрытие браузера")
    public void tearDown(ITestContext ctx) {
        com.codeborne.selenide.Selenide.closeWebDriver();
        ctx.removeAttribute("driver");
    }
}