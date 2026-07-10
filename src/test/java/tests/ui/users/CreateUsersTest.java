package tests.ui.users;

import data.UsersData;
import ui.dto.users.UserTestData;
import ui.dto.users.UserTestDataFactory;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import static io.qameta.allure.Allure.*;
import static ui.enumUI.Dropdown.USERS;
import static ui.enumUI.RadioLabel.MALE;
import static ui.enumUI.TableType.CREATE_NEW_USER;

@Epic("Пользователи")
@Feature("Создание пользователя")
public class CreateUsersTest extends BaseTest {

    UserTestData validUserData = UserTestDataFactory.getUserTestDataUI();

    @BeforeMethod
    public void testData() {
        loginPage.authorization();
        baseSteps.openTableFromDropdown(USERS, CREATE_NEW_USER);
    }

    @Owner("Лазарев Г.А.")
    @Test(testName = "Создание пользователя с валидными данными",
            groups = {"regression", "broken"})
    void successCreateUser() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Создание пользователя с валидными данными")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Создание пользователя");
        parameter("Ожидаемый результат", "Пользователь успешно создан");

        final String status = "Status: Successfully pushed, code: 201";

        usersPage.addNewUserUI(validUserData);
        baseSteps
                .verifyUnselectedRadio(MALE)
                .selectRadioLabel(MALE)
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyGetIdObject("New user ID:");
    }

    @Owner("Лазарев Г.А.")
    @Story("Создание пользователя с невалидными данными")
    @Test(testName = "Создание пользователя с пустым полем",
            groups = {"regression", "broken"},
            dataProvider = "UI. Тестовые данные для негативных проверок создания пользователя",
            dataProviderClass = UsersData.class)
    void unsuccessCreateUser(UserTestData userTestData) {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Создание пользователя с пустым полем")
        );
        parameter("Тип теста", "Негативный");
        parameter("Действие", "Создание пользователя с пустыми полями");
        parameter("Ожидаемый результат", "Ошибка валидации, пользователь не создан");

        final String status = "Status: Invalid request data";

        step("Проверка пустых полей: \"%s\"".formatted(userTestData.getDescription()), () -> {
            usersPage.addNewUserUI(userTestData);
            if (userTestData.getGender() != null) {
                baseSteps.selectRadioLabel(userTestData.getGender());
            }
            baseSteps
                    .clickPushToApi()
                    .verifyTextStatus(status)
                    .verifyNoIdObject();
        });
    }

    @Owner("Лазарев Г.А.")
    @Issue("")
    @Story("Создание пользователя с невалидными данными")
    @Test(testName = "Создание пользователя с несоответствующими данными",
            groups = {"regression", "broken"},
            dataProvider = "UI. Тестовые данные с некорректными значениями для пользователя",
            dataProviderClass = UsersData.class)
    void createUserInvalidData(UserTestData userTestData) {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Создание пользователя с несоответствующими данными")
        );
        parameter("Тип теста", "Негативный");
        parameter("Действие", "Создание пользователя с невалидными данными");
        parameter("Ожидаемый результат", "Ошибка валидации, пользователь не создан");

        final String status = "Status: AxiosError: Request failed with status code 400";

        step("Несоответствие данных: \"%s\"".formatted(userTestData.getDescription()), () -> {
            usersPage.addNewUserUI(userTestData);
            baseSteps
                    .selectRadioLabel(userTestData.getGender())
                    .clickPushToApi()
                    .verifyTextStatus(status)
                    .verifyNoIdObject();
        });
    }
}
