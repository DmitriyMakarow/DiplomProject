package test.tests.ui.houses;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.tests.BaseTest;

import static enumUI.Dropdown.HOUSES;
import static enumUI.TableType.READ_ALL_HOUSES;

@Epic("Дома")
@Feature("Чтение всех домов")
@Owner("Твоя фамилия и инициалы")
public class ReadAllHousesTest extends BaseTest {

    @BeforeMethod
    public void testData() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        baseSteps.showDropdown(HOUSES);
    }

    @Test(testName = "Проверка отображения таблицы домов")
    @Story("Отображение списка домов")
    @Description("Тест проверяет, что таблица домов корректно отображается")
    public void checkReadAllHousesTable() {
        baseSteps.openTableFromDropdown(HOUSES, READ_ALL_HOUSES);
    }
}