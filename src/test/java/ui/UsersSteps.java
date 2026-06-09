package ui;

import io.qameta.allure.Step;

import static ui.BaseLocators.getBtnUsers;

public class UsersSteps {

    @Step("Кликнуть на кнопку {0}")
    public UsersSteps clickBtn(String nameButton) {
        getBtnUsers(nameButton);
        return this;
    }
}
