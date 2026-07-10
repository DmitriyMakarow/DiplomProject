package tests.ui.houses;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.Allure.parameter;
import static ui.enumUI.Dropdown.HOUSES;
import static ui.enumUI.TableType.READ_ALL_HOUSES;

@Epic("Дома")
@Feature("Чтение всех домов")
@Owner("khvadina a.")
public class ReadAllHousesTest extends BaseTest {

    @BeforeMethod
    public void openPageAllHouses() {
        loginPage.authorization();
    }

    @Owner("Хвадина А.В.")
    @Test(testName = "Проверка отображения таблицы домов",
          groups = {"regression"})
    @Story("Отображение списка домов")
    @Description("Тест проверяет, что таблица домов корректно отображается")
    public void checkReadAllHousesTable() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка отображения таблицы домов")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Открытие таблицы домов");
        parameter("Ожидаемый результат", "Таблица домов успешно отображена");

        baseSteps.openTableFromDropdown(HOUSES, READ_ALL_HOUSES);
    }
}