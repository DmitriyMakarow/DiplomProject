package tests.ui.users;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import java.util.List;

import static ui.enumUI.Dropdown.USERS;
import static ui.enumUI.TableType.READ_ALL_USERS;

@Epic("Пользователи")
@Feature("Чтение всех пользователей")
public class ReadAllUsersTest extends BaseTest {

    @BeforeMethod
    public void testData() {
        loginPage.authorization();
    }

    @DataProvider(name = "columns")
    public Object[][] columns() {
        return new Object[][]{
                {"ID"},
                {"First"},
                {"Last"},
                {"Age"},
                {"Sex"},
                {"Money"}
        };
    }

    @Owner("Лазарев Г.А.")
    @Test(dataProvider = "columns", groups = {"regression"})
    @Story("Сортировка пользователей")
    @Description("Тест проверяет сортировку по колонке \"{columnName}\"")
    public void checkSorting(String columnName) {
        Allure.parameter("Колонка", columnName);
        baseSteps.openTableFromDropdown(USERS, READ_ALL_USERS);
        List<List<String>> initialData = baseSteps.getTableData();
        baseSteps
                .clickBtn(columnName)
                .verifyColumnNameAsc(columnName)
                .assertDataChangedAfterSort(initialData)
                .verifySortAscending(columnName)
                .clickBtn(columnName)
                .verifyColumnNameDesc(columnName)
                .verifySortDescending(columnName)
                .clickBtn("Reload")
                .assertDataRestored(initialData);
    }
}
