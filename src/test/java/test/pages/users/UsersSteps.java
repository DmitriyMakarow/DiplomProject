package test.pages.users;

import io.qameta.allure.Step;
import test.pages.base.BaseSteps;

import static test.pages.base.BaseLocators.getBtn;

public class UsersSteps extends BaseSteps {

    @Step("Кликнуть на кнопку {0}")
    public UsersSteps clickBtn(String nameButton) {
        getBtn(nameButton).click();
        return this;
    }
}
