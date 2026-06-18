package test.pages.users;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import test.pages.base.BasePage;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

public class AddMoneyPage extends BasePage {
    private final SelenideElement
            userId = $x("//*[@id='id_send']"),
            moneyfield = $x("//*[@id='money_send']"),
            pushToApi = $("#root > div > section > div > div > button.tableButton.btn.btn-primary");

    @Step("Открытие страницы Add Money")
    public AddMoneyPage openPage() {
        open("http://82.142.167.37:4881/#/update/users/plusMoney");
        return this;
    }; //open не пишем в названии метода, иначе будет зацикливание

    @Step("Добавление денег пользователю")
    public AddMoneyPage addMoneyToUser(int id, int money) {
        userId.setValue(String.valueOf(id));
        moneyfield.setValue(String.valueOf(money));
        pushToApi.shouldBe(Condition.enabled).click();
        return this;
    }

    @Step("Валидация добавления денег пользователю")
    public AddMoneyPage validationAddMoneyToUser() {
        String expectedMessage = "Status: Successfully pushed, code: 200";
        SelenideElement successMessage = $(withText(expectedMessage));
        boolean isSuccessMessageVisible = super.waitVisible(successMessage, 5);
        assertTrue(isSuccessMessageVisible,
                "Ошибка: Сообщение '%s' не появилось за отведенное время.".formatted(expectedMessage));
        return this;
    }

    @Step("Валидация ошибки добавления денег пользователю")
    public AddMoneyPage validationNotAddMoneyToUser(String message) {
        SelenideElement errorMessageElement = $(withText(message));
        boolean isMessageVisible = super.waitVisible(errorMessageElement, 5);
        assertTrue(isMessageVisible, "Ожидаемое сообщение '" + message + "' не появилось.");
                return this;
    }
}
