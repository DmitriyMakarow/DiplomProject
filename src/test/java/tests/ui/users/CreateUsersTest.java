package tests.ui.users;

import ui.dto.UserTestData;
import ui.dto.UserTestDataFactory;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.pages.users.UsersPage;
import tests.ui.base.BaseTest;

import static ui.enumUI.Dropdown.USERS;
import static ui.enumUI.RadioLabel.MALE;
import static ui.enumUI.TableType.CREATE_NEW_USER;
import static io.qameta.allure.Allure.step;

@Epic("Пользователи")
@Feature("Создание пользователя")
public class CreateUsersTest extends BaseTest {

    UserTestData validUserData = UserTestDataFactory.getUserTestData();

    @BeforeMethod
    public void testData() {
        loginPage.authorization();
        baseSteps
                .showDropdown(USERS)
                .openTableFromDropdown(USERS, CREATE_NEW_USER);
    }

    @Test(testName = "Создание пользователя с валидными данными")
    void successCreateUser() {
        final String status = "Status: Successfully pushed, code: 201";

        usersPage.addNewUser(validUserData);
        baseSteps
                .verifyUnselectedRadio(MALE)
                .selectRadioLabel(MALE)
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyGetIdObject("New user ID:");
    }

    @Story("Создание пользователя с невалидными данными")
    @Test(testName = "Создание пользователя с пустым полем",
            dataProvider = "Тестовые данные для негативных проверок создания пользователя",
            dataProviderClass = UsersPage.class)
    void unsuccessCreateUser(UserTestData userTestData) {
        final String status = "Status: Invalid request data";

        step("Проверка пустых полей: \"%s\"".formatted(userTestData.getDescription()), () -> {
            usersPage.addNewUser(userTestData);
            if (userTestData.getGender() != null) {
                baseSteps.selectRadioLabel(userTestData.getGender());
            }
            baseSteps
                    .clickPushToApi()
                    .verifyTextStatus(status)
                    .verifyNoIdObject();
        });
    }

    @Issue("")
    @Story("Создание пользователя с невалидными данными")
    @Test(testName = "Создание пользователя с несоответствующими данными",
            dataProvider = "Тестовые данные с некорректными значениями для пользователя",
            dataProviderClass = UsersPage.class)
    void createUserInvalidData(UserTestData userTestData) {
        final String status = "Status: AxiosError: Request failed with status code 400";

        step("Несоответствие данных: \"%s\"".formatted(userTestData.getDescription()), () -> {
            usersPage.addNewUser(userTestData);
            baseSteps
                    .selectRadioLabel(userTestData.getGender())
                    .clickPushToApi()
                    .verifyTextStatus(status)
                    .verifyNoIdObject();
        });
    }
}
