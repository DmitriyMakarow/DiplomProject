package tests.ui.base;

import api.adapters.BaseAdapter;
import api.adapters.CarAdapter;
import api.adapters.HouseAdapter;
import api.adapters.UserAdapter;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.CarDao;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;
import tests.db.DBConnection;
import ui.pages.allpost.AllPostPage;
import ui.pages.base.BasePage;
import ui.pages.cars.CarsPage;
import ui.pages.login.LoginPage;
import ui.pages.users.AddMoneyPage;
import ui.pages.users.IssueALoanPage;
import ui.pages.users.UsersPage;
import ui.steps.BaseSteps;
import ui.steps.UsersSteps;
import ui.wrappers.Input;
import utils.PropertyReader;
import utils.listeners.TestListener;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.open;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {

    protected final String
            baseUrl = "http://82.142.167.37:4881/#",
            user = System.getProperty("user", PropertyReader.getProperty("user")),
            password = System.getProperty("password", PropertyReader.getProperty("password")),

            urlDB = "jdbc:postgresql://82.142.167.37:4832/pflb_trainingcenter",
            userDB = System.getProperty("userDB", PropertyReader.getProperty("userDB")),
            passwordDB = System.getProperty("passwordDB", PropertyReader.getProperty("passwordDB"));

    protected DBConnection connection;
    protected CarDao carDao;
    protected BasePage basePage;
    protected BaseSteps baseSteps;
    protected LoginPage loginPage;
    protected AddMoneyPage addMoneyPage;
    protected CarAdapter carAdapter;
    protected HouseAdapter houseAdapter;
    protected UserAdapter userAdapter;
    protected CarsPage carsPage;
    protected UsersPage usersPage;
    protected IssueALoanPage issueALoanPage;
    protected AllPostPage allPostPage;
    protected UsersSteps usersSteps;

    /**
     * Настройка браузера.
     * Поддерживаемые: CHROME, FIREFOX, EDGE.
     *
     * @param browser Имя браузера (параметр запуска)
     */
    @BeforeSuite
    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    @Description("Настройка браузера")
    public void setUp(@Optional("CHROME") String browser, ITestContext context) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        String headlessProp = System.getProperty("selenide.headless", "false");
        Configuration.headless = Boolean.parseBoolean(headlessProp);
        Configuration.screenshots = true;
        Configuration.reportsFolder = "build/reports/tests";
        Configuration.savePageSource = true;
        Configuration.baseUrl = baseUrl;
        Configuration.timeout = 10000;
        Configuration.clickViaJs = true;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");

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
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                firefoxOptions.addArguments("--disable-gpu");
                Configuration.browserCapabilities = firefoxOptions;
                break;
            case "EDGE":
                Configuration.browser = "edge";
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("-inprivate");
                edgeOptions.addArguments("--disable-notifications");
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--disable-gpu");
                Configuration.browserCapabilities = edgeOptions;
                break;
            default:
                throw new IllegalArgumentException("Unknown browser: " + browser);
        }

        open(baseUrl);

        context.setAttribute("driver", WebDriverRunner.getWebDriver());

        connection = new DBConnection();
        carDao = new CarDao();
        basePage = new BasePage();
        baseSteps = new BaseSteps();
        loginPage = new LoginPage();
        addMoneyPage = new AddMoneyPage();
        carAdapter = new CarAdapter();
        houseAdapter = new HouseAdapter();
        carsPage = new CarsPage();
        usersPage = new UsersPage();
        userAdapter = new UserAdapter();
        usersSteps = new UsersSteps();
        issueALoanPage = new IssueALoanPage();
        allPostPage = new AllPostPage();
    }

    @AfterMethod(alwaysRun = true)
    @Description("Закрытие браузера")
    public void tearDown(ITestContext ctx) {
        com.codeborne.selenide.Selenide.closeWebDriver();
        ctx.removeAttribute("driver");
        connection.close();
    }
}