package ui.steps;

import org.openqa.selenium.Keys;
import ui.enumUI.Dropdown;
import ui.enumUI.RadioLabel;
import ui.enumUI.TableType;
import io.qameta.allure.Step;
import ui.pages.base.BasePage;
import utils.SortUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.testng.Assert.*;
import static ui.locators.BaseLocators.*;

public class BaseSteps extends BasePage {

    @Step("Раскрыть выпадающий список {0}")
    public BaseSteps showDropdown(Dropdown nameDropdown) {
        getItemDropdown(nameDropdown).click();
        assertTrue(waitVisible(getDropdown(nameDropdown)), "Выпадающий список у наименования \"%s\" не отображается"
                .formatted(nameDropdown));
        return this;
    }

    @Step("Открыть таблицу {1} из выпадающего списка {0}")
    public void openTableFromDropdown(Dropdown nameDropdown, TableType tableName) {
        getDescription(nameDropdown, tableName).click();
        waitForPageLoader(tableName);
    }

    @Step("Выбор радиокнопки {radioLabel}")
    public BaseSteps selectRadioLabel(RadioLabel radioLabel) {
        getRadioLabel(radioLabel).click();
        verifySelectedRadio(radioLabel);
        return this;
    }

    @Step("Увеличение значения в поле c id: \"{elementID}\" на {count} раз(а)")
    public BaseSteps stepperUp(String elementID, int count, double expectedIncrease) {
        double initialValue = Double.parseDouble(Objects.requireNonNull(getInputField(elementID)
                .getValue()).replace(",", "."));

        getInputField(elementID).hover();
        for (int i = 0; i < count; i++) {
            getInputField(elementID).sendKeys(Keys.ARROW_UP);
        }

        double currentValue = Double.parseDouble(Objects.requireNonNull(getInputField(elementID)
                .getValue()).replace(",", "."));
        double actualIncrease = currentValue - initialValue;

        assertEquals(actualIncrease, expectedIncrease, 0.001,
                """
                Значение не увеличилось на %s
                Ожидаемый результат: %s
                Фактический результат: %s
                """.formatted(expectedIncrease, expectedIncrease, actualIncrease));

        return this;
    }

    @Step("Уменьшение значения в поле с id: \"{elementID}\" на {count} раз(а)")
    public BaseSteps stepperDown(String elementID, int count, double expectedDecrease) {
        double initialValue = Double.parseDouble(Objects.requireNonNull(getInputField(elementID)
                .getValue()).replace(",", "."));

        getInputField(elementID).hover();
        for (int i = 0; i < count; i++) {
            getInputField(elementID).sendKeys(Keys.ARROW_DOWN);
        }

        double currentValue = Double.parseDouble(Objects.requireNonNull(getInputField(elementID)
                .getValue()).replace(",", "."));
        double actualDecrease = initialValue - currentValue;

        assertEquals(actualDecrease, expectedDecrease, 0.001,
                """
                Значение не уменьшилось на %s
                Ожидаемый результат: %s
                Фактический результат: %s
                """.formatted(expectedDecrease, expectedDecrease, actualDecrease));

        return this;
    }

    @Step("Проверка, что радиокнопка {radioLabel} выбрана")
    public void verifySelectedRadio(RadioLabel radioLabel) {
        assertTrue(getRadioLabel(radioLabel).isSelected(), "Радиокнопка не выбрана");
    }

    @Step("Проверка, что радиокнопка {radioLabel} не выбрана")
    public BaseSteps verifyUnselectedRadio(RadioLabel radioLabel) {
        assertFalse(getRadioLabel(radioLabel).isSelected(), "Радиокнопка выбрана");
        return this;
    }

    @Step("Ожидание загрузки таблицы {tableName}")
    public void waitForPageLoader(TableType tableName) {
        List<String> columns = tableName.getColumns();
        columns.forEach(column -> assertTrue(waitVisible(getColumnName(column)),
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

    @Step("Получение данных таблицы")
    public List<List<String>> getTableData() {
        return entryTable
                .stream()
                .limit(10)
                .map(row -> row.$$("td").texts())
                .toList();
    }

    @Step("Сравнение двух состояний таблицы")
    public boolean isTableDataEquals(List<List<String>> expectedData) {
        List<List<String>> actualData = getTableData();
        return actualData.equals(expectedData);
    }

    @Step("Проверка, что данные восстановились после Reload")
    public void assertDataRestored(List<List<String>> expectedData) {
        assertTrue(isTableDataEquals(expectedData), "Данные не восстановились после Reload");
//        verifyNoSortActive(); TODO: Расскоментировать после исправления бага
    }

    @Step("Проверка, что данные изменились после сортировки")
    public BaseSteps assertDataChangedAfterSort(List<List<String>> initialData) {
        assertFalse(isTableDataEquals(initialData),
                "Данные не изменились после сортировки");
        return this;
    }

    @Step("Проверка сортировки колонки {columnName} по возрастанию")
    public BaseSteps verifySortAscending(String columnName) {
        List<String> headers = headerTable
                .texts()
                .stream()
                .toList();

        int columnIndex = getColumnIndex(columnName, headers);

        List<String> actualData = entryTable
                .stream()
                .skip(1)
                .limit(20)
                .map(row -> row.$$("td").get(columnIndex).getText().trim())
                .toList();

        if (actualData.isEmpty()) {
            throw new IllegalStateException(
                    "Нет данных для проверки сортировки в колонке \"%s\"".formatted(columnName));
        }

        List<String> sortedData = new ArrayList<>(actualData);
        sortedData.sort(SortUtils.getStringComparator(true));

        assertEquals(sortedData, actualData,
                "Колонка \"%s\" не отсортирована по возрастанию".formatted(columnName));
        return this;
    }

    @Step("Проверка сортировки колонки {columnName} по убыванию")
    public BaseSteps verifySortDescending(String columnName) {
        List<String> headers = headerTable
                .texts()
                .stream()
                .toList();

        int columnIndex = getColumnIndex(columnName, headers);

        List<String> actualData = entryTable
                .stream()
                .skip(1)
                .limit(20)
                .map(row -> row.$$("td").get(columnIndex).getText().trim())
                .toList();

        if (actualData.isEmpty()) {
            throw new IllegalStateException(
                    "Нет данных для проверки сортировки в колонке \"%s\"".formatted(columnName));
        }

        List<String> sortedData = new ArrayList<>(actualData);
        sortedData.sort(SortUtils.getStringComparator(false));

        assertEquals(sortedData, actualData,
                "Колонка \"%s\" не отсортирована по убыванию".formatted(columnName));
        return this;
    }

    private void waitForDataChange(List<List<String>> dataBefore) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 5 * 1000L) {
            List<List<String>> dataAfter = getTableData();
            if (!dataAfter.equals(dataBefore)) {
                return;
            }
        }
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

    @Step("Получение ID из текста нового объекта")
    public String getNewObjectId() {
        String fullText = btnNewIdObject.getText();
        return fullText.replaceAll("\\D+", "");
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
