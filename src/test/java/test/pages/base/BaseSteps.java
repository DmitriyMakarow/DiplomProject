package test.pages.base;

import enumUI.Dropdown;
import enumUI.TableType;
import io.qameta.allure.Step;
import utils.SortUtils;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;
import static test.pages.base.BaseLocators.*;

public class BaseSteps extends BasePage {

    @Step("Раскрыть выпадающий список {0}")
    public void showDropdown(Dropdown nameDropdown) {
        getItemDropdown(nameDropdown).click();
        assertTrue(waitVisible(getDropdown(nameDropdown)), "Выпадающий список у наименования \"%s\" не отображается"
                .formatted(nameDropdown));
    }

    @Step("Открыть таблицу {1} из выпадающего списка {0}")
    public BaseSteps openTableFromDropdown(Dropdown nameDropdown, TableType tableName) {
        getDescription(nameDropdown, tableName).click();
        waitForPageLoader(tableName);
        return this;
    }

    @Step("Ожидание загрузки таблицы {tableName}")
    public void waitForPageLoader(TableType tableName) {
        List<String> columns = tableName.getColumns();
        columns.forEach(column -> assertTrue(waitVisible(getBtnColumnName(column)),
                "Колонки с именем: \"%s\" не существует".formatted(column)));
        assertTrue(waitVisible(entryTable.first()), "Таблица не загрузилась - первая строка не видима");
    }

    @Step("Кликнуть на кнопку {buttonName}")
    public BaseSteps clickBtn(String buttonName) {
        List<List<String>> dataBefore = getTableData();
        getBtnColumnName(buttonName).click();
        waitForDataChange(dataBefore);
        return this;
    }

    @Step("Проверка отображения сортировки по убыванию у кнопки {buttonName}")
    public BaseSteps verifyColumnNameDesc(String buttonName) {
        assertTrue(waitVisible(getBtnColumnNameDesc(buttonName)), "Должна быть стрелка вниз");
        return this;
    }

    @Step("Проверка отображения сортировки по возрастанию у кнопки {buttonName}")
    public BaseSteps verifyColumnNameAsc(String buttonName) {
        assertTrue(waitVisible(getBtnColumnNameAsc(buttonName)), "Должна быть стрелка вверх");
        return this;
    }

    @Step("Проверка, что сортировка не активна (нет стрелок)")
    public BaseSteps verifyNoSortActive() {
        assertTrue(arrowAsc.isEmpty(), "Найдена стрелка вверх - сортировка по возрастанию активна");
        assertTrue(arrowDesc.isEmpty(), "Найдена стрелка вниз - сортировка по убыванию активна");
        return this;
    }

    @Step("Кликнуть на кнопку {0}")
    public BaseSteps clickBtn(String nameButton) {
        getBtn(nameButton).click();
        return this;
    }

    @Step("Отправить запрос")
    public BaseSteps clickPushToApi() {
        assertTrue(waitVisible(btnPushToApi), "Кнопка \"PUSH TO API\" не отображается");
        btnPushToApi.click();
        return this;
    }

    @Step("Проверка сообщения о статусе запроса")
    public BaseSteps verifyTextStatus(String expectedStatus) {
        assertTrue(waitVisible(btnStatus), "Кнопка статуса не отображается");
        assertTrue(waitEqualsText(expectedStatus, btnStatus),
                """
                        Сообщение о статусе не соответствует
                        Ожидаемый результат: %s
                        Фактический результат: %s""".formatted(expectedStatus, btnStatus.getText()));
        return this;
    }

    @Step("Проверка получения id нового объекта")
    public BaseSteps verifyGetIdObject(String expectedText) {
        assertTrue(waitVisible(btnNewIdObject), "Кнопка id нового объекта не отображается");
        assertTrue(waitContainsText(expectedText, btnNewIdObject),
                """
                        Сообщение о получении id нового объекта не соответствует
                        Ожидаемый результат: %s
                        Фактический результат: %s
                        """.formatted(expectedText, btnNewIdObject));
        return this;
    }

    @Step("Проверка отсутствия id нового объекта")
    public BaseSteps verifyNoIdObject() {
        assertTrue(waitVisible(btnNewIdObject), "Кнопка id нового объекта не отображается");
        assertTrue(btnNewIdObject.getText().isEmpty());
        return this;
    }
}
