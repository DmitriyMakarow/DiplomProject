package tests.api.users;

import api.models.users.InvalidUserRequest;
import api.models.users.UserRequest;
import api.models.users.UserResponse;
import ui.dto.users.UserTestDataFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.pages.users.UsersPage;

import static io.qameta.allure.Allure.step;

@Epic("Пользователи. API")
@Feature("Создание пользователя")
public class CreateUserApiTest extends BaseTest{

    private UserResponse userResponse;
    private UserRequest userRequest;
    private Integer userId;

    @BeforeMethod
    public void createUserApi() {
        userRequest = UserTestDataFactory.postUserTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        if (userId != null) {
            userAdapter.deleteUser(userId);
        }
    }

    @Owner("Лазарев Г.А.")
    @Test(testName = "Проверка создания пользователя с валидными параметрами", groups = {"regression"})
    void checkCreateUser() {
        usersSteps
                .validateUserId(userId)
                .validateUserData(userResponse, userRequest);
    }

    @Owner("Лазарев Г.А.")
    @Test(testName = "Проверка редактирования пользователя валидными параметрами", groups = {"regression"})
    void checkEditUserValid() {
        UserRequest newUserRequest = UserTestDataFactory.putUserTestDataApi();
        UserResponse newUserResponse = userAdapter.putValidApiUser(userId, newUserRequest);
        usersSteps.validateUserData(newUserResponse, newUserRequest);
    }

    @Owner("Лазарев Г.А.")
    @Issue("#3156879")
    @Test(testName = "Проверка редактирования пользователя невалидными параметрами",
            groups = {"regression"},
            dataProvider = "Api. Тестовые данные для негативных проверок создания пользователя",
            dataProviderClass = UsersPage.class)
    void checkEditUserInvalid(InvalidUserRequest invalidUserRequest) {

        step("Проверка невалидными данными \"%s\"".formatted(invalidUserRequest.getDescription()), () -> {
            Response response = userAdapter.putInvalidApiUser(userId, invalidUserRequest);
            usersSteps.validateStatusCode(response, 400);
        });
    }
}
