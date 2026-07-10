package tests.ui.users;

import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import java.util.List;

import static io.qameta.allure.Allure.*;
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
    @Issue("После клика на \"Reload\" стрелочки не очищаются")
    @Test(dataProvider = "columns", groups = {"regression"})
    @Story("Сортировка пользователей")
    public void checkSorting(String columnName) {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Сортировка пользователей по колонке: " + columnName)
        );
        parameter("Колонка для сортировки", columnName);
        parameter("Тест", "Сортировка по колонке: " + columnName);
        step("📋 Тест проверяет сортировку по колонке \"" + columnName + "\"");

        baseSteps.openTableFromDropdown(USERS, READ_ALL_USERS);
        List<List<String>> initialData = baseSteps.getTableData();
        boolean isSkipColumn = columnName.equalsIgnoreCase("First") ||
                columnName.equalsIgnoreCase("Last");

        if (isSkipColumn) {
            addAttachment("Информация",
                    "Проверка сортировки по возрастанию для колонки '%s' пропущена из-за известного бага"
                            .formatted(columnName));
        }

        baseSteps
                .clickBtn(columnName)
                .verifyColumnNameAsc(columnName)
                .assertDataChangedAfterSort(initialData);

        // Пропускаем проверку для указанных колонок
        if (!isSkipColumn) {
            baseSteps.verifySortAscending(columnName);
        } else {
            step("Проверка сортировки по возрастанию для " + columnName + " пропущена");
        }

        baseSteps
                .clickBtn(columnName)
                .verifyColumnNameDesc(columnName)
                .verifySortDescending(columnName)
                .clickBtn("Reload")
                .assertDataRestored(initialData);
    }
}
