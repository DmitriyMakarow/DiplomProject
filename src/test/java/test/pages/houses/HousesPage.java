package test.pages.houses;

import test.pages.base.BasePage;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text; // ← добавили этот импорт!
import com.codeborne.selenide.SelenideElement;

public class HousesPage extends BasePage {

    // локаторы полей
    private final SelenideElement floorsField = $("#floor_send");
    private final SelenideElement priceField = $("#price_send");
    private final SelenideElement parkingFirst = $("#parking_first_send");

    // кнопка и статус
    private final SelenideElement pushButton = $x("//button[contains(., 'PUSH')]");
    private final SelenideElement statusLabel = $x("//span[contains(text(), 'Status: pushed')]");

    public HousesPage() {
        // конструктор пустой
    }

    // переход к форме создания дома через меню
    public void navigateToCreateForm() {
        // кликаем по меню Houses (ищем элемент с текстом Houses)
        $$("a, span").filter(text("Houses")).first().click();

        // кликаем по пункту Create new в выпавшем меню
        $$("a").filter(text("Create new")).first().click();

        // ждем, что поле floors появилось
        floorsField.shouldBe(visible);
    }

    // заполнение и отправка формы
    public void fillAndSubmit(String floors, String price, String parking) {
        floorsField.setValue(floors);
        priceField.setValue(price);
        parkingFirst.setValue(parking);

        pushButton.click();

        final SelenideElement statusLabel = $x("//*[contains(., 'Successfully pushed')]");
    }
}