package ui.steps;

import api.models.users.UserRequest;
import api.models.users.UserResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ui.pages.base.BasePage;

import java.util.List;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;
import static ui.steps.BaseSteps.*;

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

    @Step("Проверка ID пользователя {userId} и его машин {carsIdList}")
    public UsersSteps assertUserAndCarsExist(Integer userId, List<Integer> carsIdList) {
        // Проверка пользователя
        assertValuesExistsInTable("tableUser", List.of(userId.toString()));

        // Проверка машин
        List<String> carIds = carsIdList.stream()
                .map(String::valueOf)
                .toList();

        assertValuesExistsInTable("tableCars", carIds);
        return this;
    }

    /*
     Проверяет соответствие количества машин в таблице пользователя и таблице машин

     Алгоритм работы:
     1. Извлекает все значения из колонки "Cars" таблицы пользователей
     2. Получает количество машин из первой строки (так как в таблице всегда один пользователь)
     3. Получает все строки данных из таблицы машин
     4. Вычисляет количество строк в таблице машин (фактическое количество машин)
     5. Сравнивает ожидаемое количество (из колонки Cars) с фактическим (количество строк в таблице машин)
     6. При несовпадении выбрасывает AssertionError с детальным описанием ошибки
     7. Возвращает текущий экземпляр UsersSteps для поддержки цепочечных вызовов

     @param userTableTitle - название таблицы пользователей (например: "tableUser")
     @param carsTableTitle - название таблицы машин (например: "tableCars")
     @return UsersSteps - текущий экземпляр для построения цепочки вызовов
     @throws AssertionError если ожидаемое количество машин не совпадает с фактическим
     */
    @Step("Проверка, что количество машин в колонке Cars таблицы {userTableTitle} " +
            "соответствует количеству строк в таблице {carsTableTitle}")
    public UsersSteps assertCarCountMatches(String userTableTitle, String carsTableTitle) {
        List<String> carsColumn = getColumnValues(userTableTitle, "Cars");
        int expectedCarCount = Integer.parseInt(carsColumn.get(0));
        int actualCarCount = getTableData(carsTableTitle).size();

        assertEquals(expectedCarCount, actualCarCount,
                "Количество машин не совпадает! В колонке Cars: %d, в таблице машин: %d"
                        .formatted(expectedCarCount, actualCarCount));
        return this;
    }
}
