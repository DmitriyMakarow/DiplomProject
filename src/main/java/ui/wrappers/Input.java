package ui.wrappers;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.$x;

@Log4j2
public class Input {

    String label;
    SelenideElement element;

    public Input(String label) {
        this.label = label;
        this.element = null;
    }

    // конструктор для дублирующихся ID с индексом формы
    public Input(int formIndex, String label) {
        this.label = label;
        this.element = $x(String.format(
                "(//div[./hr and ./table])[%d]//input[@id='%s']",
                formIndex, label));
    }

    @Step("Заполнение поля {label} значением {text}")
    public void fillField(String text) {
        log.info("Writing '{}' in to '{}'", text, label);
        if (element != null) {
            element.setValue(text);
        } else {
            // совпадение ID для уникальных полей
            $x("//input[@id='%s']".formatted(label)).setValue(text);
        }
    }
}