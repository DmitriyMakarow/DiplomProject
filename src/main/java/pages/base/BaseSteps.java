package pages.base;

import io.qameta.allure.Step;

import static org.testng.Assert.assertTrue;
import static pages.base.BaseLocators.*;

public class BaseSteps extends BasePage {

    @Step("Раскрыть выпадающий список {0}")
    public BaseSteps showDropdown(String nameDropdown) {
        getItemDropdown(nameDropdown).click();
        assertTrue(waitVisible(getDropdown(nameDropdown)), "Выпадающий список у наименования \"%s\" не отображается"
                .formatted(nameDropdown));
        return this;
    }

    @Step("Открыть таблицу {1} из выпадающего списка {0}")
    public BaseSteps openTableFromDropdown(String nameDropdown, String tableName) {
        getDescription(nameDropdown, tableName).click();
        return this;
    }

    @Step("Кликнуть на кнопку {0}")
    public BaseSteps clickBtn(String nameButton) {
        getBtn(nameButton).click();
        return this;
    }

    @Step("Отправить запрос")
    public BaseSteps clickPushToApi() {
        assertTrue(waitVisible(btnPushToApi), "Кнопка \"PUSH TO API\" не отображается");
        btnPushToApi.click();
        return this;
    }

    @Step("Проверка сообщения о статусе запроса")
    public BaseSteps verifyTextStatus(String expectedStatus) {
        assertTrue(waitVisible(btnStatus), "Кнопка статуса не отображается");
        assertTrue(waitEqualsText(expectedStatus, btnStatus),
                """
                        Сообщение о статусе не соответствует
                        Ожидаемый результат: %s
                        Фактический результат: %s""".formatted(expectedStatus, btnStatus.getText()));
        return this;
    }

    @Step("Проверка получения id нового объекта")
    public BaseSteps verifyGetIdObject(String expectedText) {
        assertTrue(waitVisible(btnNewIdObject), "Кнопка id нового объекта не отображается");
        assertTrue(waitContainsText(expectedText, btnNewIdObject),
                """
                        Сообщение о получении id нового объекта не соответствует
                        Ожидаемый результат: %s
                        Фактический результат: %s
                        """.formatted(expectedText, btnNewIdObject));
        return this;
    }

    @Step("Проверка отсутствия id нового объекта")
    public BaseSteps verifyNoIdObject() {
        assertTrue(waitVisible(btnNewIdObject), "Кнопка id нового объекта не отображается");
        assertTrue(btnNewIdObject.getText().isEmpty());
        return this;
    }
}
