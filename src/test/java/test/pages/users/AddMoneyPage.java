package test.pages.users;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import test.pages.base.BasePage;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

public class AddMoneyPage extends BasePage {
    private final SelenideElement
            USER_ID = $x("//*[@id='id_send']"),
            MONEY= $x("//*[@id='money_send']"),
            PUSH_TO_API = $("#root > div > section > div > div > button.tableButton.btn.btn-primary");

    @Step("Открытие страницы Add Money")
    public AddMoneyPage openPage (){
        open("http://82.142.167.37:4881/#/update/users/plusMoney");
        return this;
    }; //open не пишем в названии метода, иначе будет зацикливание


    @Step("Добавление денег пользователю")
    public AddMoneyPage addMoneyToUser(int id, int money){
        $(USER_ID).setValue(String.valueOf(id));
        $(MONEY).setValue(String.valueOf(money));
        $(PUSH_TO_API).shouldBe(Condition.enabled).click();
        return this;
    }

    @Step("Валидация добавления денег пользователю")
    public AddMoneyPage validationAddMoneyToUser(){
        $(withText("Status: Successfully pushed, code: 200")).shouldBe(visible);
        return this;

    }

    @Step("Валидация ошибки добавления денег пользователю")
    public AddMoneyPage validationNotAddMoneyToUser(String message){
        $(withText(message)).shouldBe(visible);
        return this;

    }
}
