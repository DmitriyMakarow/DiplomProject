package test.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.testng.annotations.DataProvider;
import test.pages.base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginPage extends BasePage {

    private final SelenideElement
            emailField = $x("//input[@name='email']"),
            passwordField = $x("//input[@name='password']"),
            btnGo = $x("//button[text()=' GO']"),
            errorMessageUserField = $x("//div[contains(@style,'red')][1]"),
            errorMessagePasswordField = $x("//div[contains(@style,'red')][2]"),
            title = $x("//div[contains(text(), 'Authorization')]");

    @Step("Авторизация валидными данными")
    public void authorization() {
        verifyOpenLoginPage()
                .login(user, password)
                .verifySuccessAuthorization();
    }

    @Step("Авторизоваться с данными: Логин - {0}, Пароль - {1}")
    public LoginPage authorization(String user, String password) {
        verifyOpenLoginPage();
        login(user, password);
        return this;
    }

    @Step("Проверка открытия страницы авторизации")
    public LoginPage verifyOpenLoginPage() {
        title.shouldBe(Condition.visible);
        return this;
    }

    public LoginPage login(String user, String password) {
        emailField.setValue(user);
        passwordField.setValue(password);
        btnGo.click();
        return this;
    }

    @Step("Проверка сообщения об успешной авторизации")
    public LoginPage verifySuccessAuthorization() {
        String alertText = confirm();
        assertEquals(alertText, "Successful authorization", "Авторизация не прошла");
        assertTrue(btnGo.isDisplayed());
        return this;
    }

    @Step("Проверка сообщения об ошибке авторизации")
    public LoginPage verifyErrorAuthorization(String expectedText) {
        String alertText = confirm();
        assertEquals(alertText, expectedText, "Авторизация прошла");
        assertTrue(btnGo.isDisplayed());
        return this;
    }

    @Step("Проверка сообщения об ошибке заполнения поля user")
    public LoginPage verifyErrorMessageInvalidUser(String expectedText) {
        assertTrue(waitVisible(errorMessageUserField), "Не отображается сообщение об ошибке заполнения поля user");
        assertEquals(expectedText, errorMessageUserField.getText(), "Текст ошибки заполнения поля user не соответствует");
        return this;
    }

    @Step("Проверка сообщения об ошибке заполнения поля password")
    public LoginPage verifyErrorMessageInvalidPassword(String expectedText) {
        assertTrue(waitVisible(errorMessagePasswordField), "Не отображается сообщение об ошибке заполнения поля password");
        assertEquals(expectedText, errorMessagePasswordField.getText(), "Текст ошибки заполнения поля password не соответствует");
        return this;
    }

    @DataProvider(name = "Тестовые данные для негативных проверок авторизации")
    public Object[][] loginData() {
        return new Object[][] {
                {"", password, ""},
                {user, "", ""},
                {"", "", "Incorrect input data"}
        };
    }
}
