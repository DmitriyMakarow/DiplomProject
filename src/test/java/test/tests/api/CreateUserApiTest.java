package test.tests.api;

import api.models.InvalidUserRequest;
import api.models.UserRequest;
import api.models.UserResponse;
import dto.UserTestDataFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.pages.users.UsersPage;
import test.tests.BaseTest;

import static io.qameta.allure.Allure.step;

@Epic("Пользователи. API")
@Feature("Создание пользователя")
@Owner("Lazarev G.A")
public class CreateUserApiTest extends BaseTest {

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

    @Test(testName = "Проверка создания пользователя с валидными параметрами")
    void checkCreateUser() {
        usersSteps
                .validateUserId(userId)
                .validateUserData(userResponse, userRequest);
    }

    @Test(testName = "Проверка редактирования пользователя валидными параметрами")
    void checkEditUserValid() {
        UserRequest newUserRequest = UserTestDataFactory.putUserTestDataApi();
        UserResponse newUserResponse = userAdapter.putValidApiUser(userId, newUserRequest);
        usersSteps.validateUserData(newUserResponse, newUserRequest);
    }

    @Issue("#3156879")
    @Test(testName = "Проверка редактирования пользователя невалидными параметрами",
            dataProvider = "Api. Тестовые данные для негативных проверок создания пользователя",
            dataProviderClass = UsersPage.class)
    void checkEditUserInvalid(InvalidUserRequest invalidUserRequest) {

        step("Проверка невалидными данными \"%s\"".formatted(invalidUserRequest.getDescription()), () -> {
            Response response = userAdapter.putInvalidApiUser(userId, invalidUserRequest);
            usersSteps.validateStatusCode(response, 400);
        });
    }
}
