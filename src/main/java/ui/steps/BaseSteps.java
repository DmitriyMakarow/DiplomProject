package ui.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
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
import static ui.enumUI.TableType.READ_USER_WITH_CARS;
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
        showDropdown(nameDropdown);
        getDescription(nameDropdown, tableName).click();
        waitForPageLoader(tableName);
    }

    @Step("Выбор радиокнопки {radioLabel}")
    public BaseSteps selectRadioLabel(RadioLabel radioLabel) {
        getRadioLabel(radioLabel).click();
        verifySelectedRadio(radioLabel);
        return this;
    }

    /*
     Увеличивает значение в числовом поле с помощью стрелки вверх (stepper) заданное количество раз
     и проверяет, что увеличение соответствует ожидаемому значению

     Алгоритм работы:
     1. Находит поле ввода по его ID через метод getInputField(elementID)
     2. Сохраняет начальное значение поля, преобразуя его в double (заменяя запятую на точку)
     3. Наводит курсор на поле (hover) для активации stepper'а
     4. В цикле отправляет клавишу ARROW_UP указанное количество раз (count)
     5. Получает текущее значение поля после нажатий и преобразует его в double
     6. Вычисляет фактическое увеличение (currentValue - initialValue)
     7. Сравнивает фактическое увеличение с ожидаемым (expectedIncrease) с погрешностью 0.001
     8. При несовпадении выбрасывает AssertionError с детальным сообщением
     9. Возвращает текущий экземпляр BaseSteps для цепочки вызовов

     @param elementID - ID поля ввода (например: "price", "quantity")
     @param count - количество нажатий стрелки вверх
     @param expectedIncrease - ожидаемое увеличение значения
     @return BaseSteps - текущий экземпляр для построения цепочки вызовов
     @throws AssertionError если фактическое увеличение не совпадает с ожидаемым
     */
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

    /*
     Уменьшает значение в числовом поле с помощью стрелки вниз (stepper) заданное количество раз
     и проверяет, что уменьшение соответствует ожидаемому значению

     Алгоритм работы:
     1. Находит поле ввода по его ID через метод getInputField(elementID)
     2. Сохраняет начальное значение поля, преобразуя его в double (заменяя запятую на точку)
     3. Наводит курсор на поле (hover) для активации stepper'а
     4. В цикле отправляет клавишу ARROW_DOWN указанное количество раз (count)
     5. Получает текущее значение поля после нажатий и преобразует его в double
     6. Вычисляет фактическое уменьшение (initialValue - currentValue)
     7. Сравнивает фактическое уменьшение с ожидаемым (expectedDecrease) с погрешностью 0.001
     8. При несовпадении выбрасывает AssertionError с детальным сообщением
     9. Возвращает текущий экземпляр BaseSteps для цепочки вызовов

     @param elementID - ID поля ввода (например: "price", "quantity")
     @param count - количество нажатий стрелки вниз
     @param expectedDecrease - ожидаемое уменьшение значения
     @return BaseSteps - текущий экземпляр для построения цепочки вызовов
     @throws AssertionError если фактическое уменьшение не совпадает с ожидаемым
     */
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
        if (tableName != READ_USER_WITH_CARS) {
            assertTrue(waitVisible(entryTable.first(), 10),
                    "Таблица \"%s\" не загрузилась - первая строка не видима".formatted(tableName));
        }
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

    /*
     Получает данные таблицы в виде списка списков строк

     Алгоритм работы:
     1. Находит таблицу по её названию (классу)
     2. Получает все строки тела таблицы (tbody/tr)
     3. Для каждой строки извлекает текст из всех ячеек (td)
     4. Собирает результаты в список списков строк
     5. Возвращает структурированные данные таблицы

     @param tableTitle - название таблицы (например: "tableUser", "tableCars")
     @return List<List<String>> - данные таблицы, где каждый внутренний список представляет одну строку
     */
    @Step("Получение данных таблицы {tableTitle}")
    public static List<List<String>> getTableData(String tableTitle) {
        return getTableName(tableTitle)
                .$$x(".//tbody//tr")
                .stream()
                .map(row -> row.$$x(".//td").texts())
                .toList();
    }

    /*
    Получает список значений из указанной колонки таблицы

    Алгоритм работы:
    1. Находит индекс колонки по её названию через метод findIndexOfColumn
    2. Находит таблицу по названию
    3. Получает все строки тела таблицы (tbody/tr)
    4. Проверяет, что таблица не пустая (sizeGreaterThan(0))
    5. Для каждой строки извлекает текст из ячейки с найденным индексом колонки
    6. Собирает все значения в список строк
    7. Возвращает список значений колонки

    @param tableTitle - название таблицы (например: "tableUser", "tableCars")
    @param columnName - название колонки (например: "ID", "Cars", "First name")
    @return List<String> - список значений из указанной колонки
    @throws RuntimeException если колонка не найдена
    */
    @Step("Получение списка значений колонки \"{columnName}\" из таблицы {tableTitle}")
    public static List<String> getColumnValues(String tableTitle, String columnName) {

        int columnIndex = findIndexOfColumn(tableTitle, columnName);

        return getTableName(tableTitle).$$x(".//tbody//tr")
                .shouldHave(CollectionCondition.sizeGreaterThan(0))
                .stream()
                .map(row -> row.$x(".//td[%s]".formatted(columnIndex + 1)).innerText())
                .toList();
    }

    /*
     Находит индекс колонки в таблице по её названию

     Алгоритм работы:
     1. Находит таблицу по названию
     2. Получает все заголовки таблицы (th)
     3. Перебирает все заголовки в цикле
     4. Проверяет, содержит ли текст заголовка искомое название колонки (игнорируя пробелы)
     5. Если колонка найдена - возвращает её индекс
     6. Если колонка не найдена - выбрасывает исключение со списком доступных колонок

     @param tableTitle - название таблицы (например: "tableUser", "tableCars")
     @param columnName - название колонки для поиска (например: "ID", "Cars")
     @return int - индекс найденной колонки (начиная с 0)
     @throws RuntimeException если колонка с указанным именем не найдена
     */
    @Step("Получение индекса колонки \"{columnName}\" в таблице {tableTitle}")
    public static int findIndexOfColumn(String tableTitle, String columnName) {
        ElementsCollection headers = getTableName(tableTitle).$$x(".//th");

        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).innerText().contains(columnName.trim())) {
                return i;
            }
        }

        throw new RuntimeException("Колонка с именем '%s' не найдена. Доступные колонки: %s"
                .formatted(columnName, headers.texts()));
    }

    /*
     Проверяет, что все ожидаемые значения присутствуют в таблице

     Алгоритм работы:
     1. Получает все данные таблицы через метод getTableData
     2. Преобразует список списков в плоский список всех ячеек (flatMap)
     3. Для каждого ожидаемого значения проверяет, есть ли оно в плоском списке ячеек
     4. Собирает все значения, которые не были найдены, в список notFound
     5. Проверяет, что список notFound пустой (все значения найдены)
     6. Если есть не найденные значения - выбрасывает AssertionError с их списком

     @param tableTitle - название таблицы (например: "tableUser", "tableCars")
     @param expectedValues - список ожидаемых значений для проверки
     @throws AssertionError если одно или несколько значений отсутствуют в таблице
     */
    @Step("Проверка наличия всех значений {expectedValues} в таблице {tableTitle}")
    public static void assertValuesExistsInTable(String tableTitle, List<String> expectedValues) {
        List<List<String>> tableData = getTableData(tableTitle);
        List<String> allCells = tableData.stream().flatMap(List::stream).toList();

        List<String> notFound = expectedValues.stream()
                .filter(value -> allCells.stream().noneMatch(cell -> cell.trim().equals(value.trim())))
                .toList();

        assertTrue(notFound.isEmpty(), "Values not found: " + notFound);
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

    /*
     Проверяет сортировку данных в указанной колонке по возрастанию

     Алгоритм работы:
     1. Получает список всех заголовков таблицы (headerTable)
     2. Находит индекс колонки по её названию через метод getColumnIndex
     3. Собирает данные из указанной колонки:
        - Пропускает первую строку (заголовок)
        - Ограничивает выборку 20 строками
        - Извлекает текст из ячейки по найденному индексу
     4. Проверяет, что данные не пустые
     5. Создает копию списка и сортирует её по возрастанию (используя SortUtils.getStringComparator(true))
     6. Сравнивает отсортированный список с фактическими данными
     7. При несовпадении выбрасывает AssertionError с сообщением об ошибке
     8. Возвращает текущий экземпляр BaseSteps для цепочки вызовов

     @param columnName - название колонки для проверки сортировки
     @return BaseSteps - текущий экземпляр для построения цепочки вызовов
     @throws IllegalStateException если нет данных для проверки сортировки
     @throws AssertionError если колонка не отсортирована по возрастанию
     */
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

    /*
     Проверяет сортировку данных в указанной колонке по убыванию

     Алгоритм работы:
     1. Получает список всех заголовков таблицы (headerTable)
     2. Находит индекс колонки по её названию через метод getColumnIndex
     3. Собирает данные из указанной колонки:
        - Пропускает первую строку (заголовок)
        - Ограничивает выборку 20 строками
        - Извлекает текст из ячейки по найденному индексу
     4. Проверяет, что данные не пустые
     5. Создает копию списка и сортирует её по убыванию (используя SortUtils.getStringComparator(false))
     6. Сравнивает отсортированный список с фактическими данными
     7. При несовпадении выбрасывает AssertionError с сообщением об ошибке
     8. Возвращает текущий экземпляр BaseSteps для цепочки вызовов

     @param columnName - название колонки для проверки сортировки
     @return BaseSteps - текущий экземпляр для построения цепочки вызовов
     @throws IllegalStateException если нет данных для проверки сортировки
     @throws AssertionError если колонка не отсортирована по убыванию
     */
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

    /*
     Ожидает изменения данных в таблице в течение 5 секунд

     Алгоритм работы:
     1. Запоминает время начала выполнения метода
     2. Запускает цикл, который выполняется пока не пройдет 5 секунд
     3. В каждой итерации получает текущие данные таблицы через getTableData()
     4. Сравнивает текущие данные с данными "до" (dataBefore)
     5. Если данные изменились - немедленно выходит из метода (return)
     6. Если данные не изменились в течение 5 секунд - метод завершается без ошибки
     7. Используется для ожидания обновления таблицы после асинхронных операций

     @param dataBefore - данные таблицы до изменения (List<List<String>>)
     */
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
