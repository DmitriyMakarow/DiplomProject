package ui.pages.users;

import ui.dto.users.UserTestData;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

public class UsersPage extends BasePage {

    public void addNewUserUI(UserTestData userTestData) {
        new Input("first_name").fillField(userTestData.getFirstName());
        new Input("last_name").fillField(userTestData.getLastName());
        new Input("age").fillField(userTestData.getAge());
        new Input("money").fillField(userTestData.getMoney());
    }
}
