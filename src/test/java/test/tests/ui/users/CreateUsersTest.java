package test.tests.ui.users;

import dto.UserTestData;
import dto.UserTestDataFactory;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.pages.users.UsersPage;
import test.tests.BaseTest;

import static enumUI.Dropdown.USERS;
import static enumUI.RadioLabel.MALE;
import static enumUI.TableType.CREATE_NEW_USER;
import static io.qameta.allure.Allure.step;

@Epic("Пользователи")
@Feature("Создание пользователя")
@Owner("Lazarev G.A")
public class CreateUsersTest extends BaseTest {

    UserTestData validUserData = UserTestDataFactory.getUserTestDataUI();

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

        usersPage.addNewUserUI(validUserData);
        baseSteps
                .verifyUnselectedRadio(MALE)
                .selectRadioLabel(MALE)
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyGetIdObject("New user ID:");
    }

    @Story("Создание пользователя с невалидными данными")
    @Test(testName = "Создание пользователя с пустым полем",
            dataProvider = "UI. Тестовые данные для негативных проверок создания пользователя",
            dataProviderClass = UsersPage.class)
    void unsuccessCreateUser(UserTestData userTestData) {
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

    @Issue("")
    @Story("Создание пользователя с невалидными данными")
    @Test(testName = "Создание пользователя с несоответствующими данными",
            dataProvider = "UI. Тестовые данные с некорректными значениями для пользователя",
            dataProviderClass = UsersPage.class)
    void createUserInvalidData(UserTestData userTestData) {
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
