package ui.users;

import io.qameta.allure.Step;
import ui.BaseSteps;

import static ui.users.UsersPage.getBtnUsers;

public class UsersSteps extends BaseSteps {

    @Step("Кликнуть на кнопку {0}")
    public UsersSteps clickBtn(String nameButton) {
        getBtnUsers(nameButton).click();
        return this;
    }
}
