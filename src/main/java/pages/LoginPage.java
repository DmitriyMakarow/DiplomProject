package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

public class LoginPage extends BasePage {

    private final SelenideElement
            emailField = $x("//input[@name='email']"),
            passwordField = $x("//input[@name='password']"),
            btnGo = $x("//button[text()=' GO']"),
            errorMessage = $x("//div[contains(@style,'red')]"),
            title = $x("//div[contains(text(), 'Authorization')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Авторизоваться с данными: Логин - {0}, Пароль - {1}")
    public LoginPage authorization(String user, String password) {
        openLoginPage();
        login(user, password);
        return this;
    }

    public void openLoginPage() {
        open("/#");
        title.shouldBe(Condition.visible);
    }

    public void login(String user, String password) {
        emailField.setValue(user);
        passwordField.setValue(password);
        btnGo.click();
    }

    public void verifySuccessAuthorization() {
        String alertText = confirm();
        assertEquals(alertText, "Successful authorization", "Авторизация не прошла");
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
