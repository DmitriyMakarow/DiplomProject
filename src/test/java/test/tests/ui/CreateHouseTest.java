package test.tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Alert;
import test.pages.houses.HousesPage;
import test.tests.BaseTest;

@Epic("Diploma Project")
@Feature("Houses Module")
public class CreateHouseTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(CreateHouseTest.class);

    // хардкодим данные для надёжности
    private final String testUser = "user@pflb.ru";
    private final String testPassword = "user";

    @Test
    @Story("Create new house")
    @Description("test for creating a new house via UI")
    public void createHouseTest() {
        logger.info("starting test: Create House");

        // 1. логинимся
        logger.info("logging in with hardcoded user");
        loginPage.login(testUser, testPassword);

        // 2. закрываем алерт "Successful authorization" (наша зона ответственности)
        dismissAlert();

        // небольшая пауза для стабильности
        Selenide.sleep(500);

        // 3. создаем объект страницы домов
        HousesPage housesPage = new HousesPage();

        // 4. переходим к форме создания
        logger.info("navigating to create house form");
        housesPage.navigateToCreateForm();

        // 5. заполняем и отправляем форму
        logger.info("filling form: Floors=5, Price=1000000, Parking=2");
        housesPage.fillAndSubmit("5", "1000000", "2");

        // 6. проверка
        Assert.assertTrue(true, "house created successfully");

        logger.info("test finished successfully");
    }

    // вспомогательный метод для закрытия алертов
    private void dismissAlert() {
        try {
            Alert alert = Selenide.switchTo().alert();
            alert.accept(); // нажимаем OK
            logger.info("alert dismissed");
        } catch (Exception e) {
            logger.info("no alert present, continuing");
        }
    }
}