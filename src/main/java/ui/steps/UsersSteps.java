package ui.steps;

import api.models.UserRequest;
import api.models.UserResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ui.pages.base.BasePage;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class UsersSteps extends BasePage {

    @Step("Проверка ID пользователя")
    public UsersSteps validateUserId(Integer userId) {
        assertNotNull(userId, "ID пользователя не должен быть null");
        assertTrue(userId > 0, "ID пользователя должен быть положительным числом");
        return this;
    }

    @Step("Проверка всех полей созданного пользователя")
    public void validateUserData(UserResponse userResponse, UserRequest userRequest) {
        validateFirstName(userResponse, userRequest);
        validateSecondName(userResponse, userRequest);
        validateAge(userResponse, userRequest);
        validateSex(userResponse, userRequest);
        validateMoney(userResponse, userRequest);
    }

    @Step("Проверка имени пользователя")
    public void validateFirstName(UserResponse userResponse, UserRequest userRequest) {
        assertEquals(userResponse.getFirstName(), userRequest.getFirstName(),
                """
                Имя не совпадает.
                Ожидаемый результат: %s
                Фактический результат: %s
                """.formatted(userRequest.getFirstName(), userResponse.getFirstName()));
    }

    @Step("Проверка фамилии пользователя")
    public void validateSecondName(UserResponse userResponse, UserRequest userRequest) {
        assertEquals(userResponse.getSecondName(), userRequest.getSecondName(),
                """
                Фамилия не совпадает.
                Ожидаемый результат: %s
                Фактический результат: %s
                """.formatted(userRequest.getSecondName(), userResponse.getSecondName()));
    }

    @Step("Проверка возраста пользователя")
    public void validateAge(UserResponse userResponse, UserRequest userRequest) {
        assertEquals(userResponse.getAge(), userRequest.getAge(),
                """
                Возраст не совпадает.
                Ожидаемый результат: %d
                Фактический результат: %d
                """.formatted(userRequest.getAge(), userResponse.getAge()));
    }

    @Step("Проверка пола пользователя")
    public void validateSex(UserResponse userResponse, UserRequest userRequest) {
        assertEquals(userResponse.getSex(), userRequest.getSex(),
                """
                Пол не совпадает.
                Ожидаемый результат: %s
                Фактический результат: %s
                """.formatted(userRequest.getSex(), userResponse.getSex()));
    }

    @Step("Проверка баланса пользователя")
    public void validateMoney(UserResponse userResponse, UserRequest userRequest) {
        assertEquals(userResponse.getMoney(), userRequest.getMoney(), 0.01,
                """
                Баланс не совпадает.
                Ожидаемый результат: %.2f
                Фактический результат: %.2f
                """.formatted(userRequest.getMoney(), userResponse.getMoney()));
    }

    @Step("Проверка статус-кода ответа")
    public void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, expectedStatusCode,
                """
                Статус-код не совпадает.
                Ожидаемый результат: %d
                Фактический результат: %d
                """.formatted(expectedStatusCode, actualStatusCode));
    }
}
