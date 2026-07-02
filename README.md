# PFLB Test Automation Project Team2

Командный проект по автоматизации тестирования учебного полигона PFLB Test-API

---

## 📋 О проекте

Проект демонстрирует полный цикл автоматизации тестирования:
UI → API → DB → Allure Report → CI/CD

Цель проекта: реализация автотестов с проверками на трех уровнях:
- **UI** — тестирование пользовательского интерфейса (Selenide)
- **API** — тестирование backend-методов (RestAssured)
- **DB** — проверка данных в PostgreSQL (JDBC)

---

## 🎯 Что тестировали

Основные сущности системы:
- **Users** (Пользователи)
- **Cars** (Автомобили)
- **Houses** (Дома)

---

## ✅ Чек-лист тестирования

### Login (Авторизация)
- [ ] **UI**: Успешный логин с валидными данными (`LoginTest`)

### Users (Пользователи)
- [ ] **UI**: Создание пользователя (`CreateUsersTest`)
- [ ] **UI**: Получение списка всех пользователей (`ReadAllUsersTest`)
- [ ] **UI**: Добавление денег пользователю (`AddMoneyTest`)
- [ ] **UI**: Выдача кредита пользователю (`IssueALoanTest`)
- [ ] **API**: Создание пользователя (`CreateUserApiTest`)
- [ ] **API**: Добавление денег пользователю (`AddMoneyApiTest`)
- [ ] **API**: Выдача кредита пользователю (`LoanAPITest`)

### Cars (Автомобили)
- [ ] **UI**: Создание автомобиля (`CreateCarTest`)
- [ ] **UI**: Получение списка всех автомобилей (`ReadAllCarsTest`)
- [ ] **UI**: Покупка/продажа автомобиля (`BuyOrSellCarTest`)
- [ ] **API**: CRUD операции с автомобилями (`CarApiTest`)
- [ ] **API**: Покупка автомобиля (`BuyCarApiTest`)
- [ ] **API**: Продажа автомобиля (`SellCarApiTest`)

### Houses (Дома)
- [ ] **UI**: Создание дома (`CreateHouseTest`)
- [ ] **UI**: Получение списка всех домов (`ReadAllHousesTest`)
- [ ] **UI**: Получение дома по ID (`ReadHouseByIdTest`)
- [ ] **API**: CRUD операции с домами (`HouseApiTest`)

### ALL POST
- [ ] **UI**: Создание пользователя через ALL POST (`AllPostTest`)
- [ ] **UI**: Добавление денег пользователю через ALL POST (`AllPostTest`)
- [ ] **UI**: Создание автомобиля через ALL POST (`AllPostTest`)
- [ ] **UI**: Создание дома через ALL POST (`AllPostTest`)
- [ ] **UI**: Заселение/выселение пользователя через ALL POST (`AllPostTest`)
- [ ] **UI**: Покупка/продажа автомобиля через ALL POST (`AllPostTest`)

### Database (Проверка данных в БД)
- [ ] **DB**: Пример подключения к БД и получения данных (`DBTest`)


---

## 🛠️ Технологический стек

| Направление | Технологии |
|-------------|------------|
| Язык | Java 17 |
| Сборка | Maven 3.9.6 |
| UI-тесты | Selenide 7.16.2 |
| API-тесты | RestAssured 6.0.0 |
| Test Framework | TestNG 7.12.0 |
| Генерация данных | JavaFaker 1.0.2, DataFaker 2.5.4 |
| База данных | PostgreSQL 42.7.11, JDBC |
| Отчетность | Allure 2.24.0 |
| Валидация API | json-schema-validator 6.0.0 |
| Работа с JSON | Gson 2.14.0 |
| Дополнительно | Lombok 1.18.46 |
| Логирование | log4j2 2.26.0 |
| CI/CD | Jenkins |
| Браузеры | Chrome, Firefox, Edge |

---

## 🏗️ Архитектура проекта

**Используемые паттерны:**

1. **Page Object** — каждая страница UI описана как отдельный класс. Методы класса инкапсулируют взаимодействие с элементами страницы (заполнение форм, клики, проверки).
    - `LoginPage` — страница авторизации
    - `AllPostPage` — страница ALL POST
    - `HousesPage`, `CarsPage`, `UsersPage` — страницы сущностей

2. **Data Factory** — фабрики для генерации тестовых данных через DataFaker/JavaFaker. Позволяют создавать валидные и невалидные данные одним вызовом.
    - `UserTestDataFactory` — генерация данных пользователя
    - `CarTestDataFactory` — генерация данных автомобиля

3. **Adapter** — адаптеры для работы с API. Инкапсулируют HTTP-запросы (GET, POST, PUT, DELETE) и работу с ответами.
    - `UserAdapter` — CRUD операции с пользователями
    - `CarAdapter` — CRUD операции с автомобилями
    - `HouseAdapter` — CRUD операции с домами

4. **Base Test** — базовый класс с общей логикой setUp/tearDown. Выполняет авторизацию и навигацию перед каждым тестом.
    - `BaseTest` — общий предок для всех UI тестов

5. **DTO (Data Transfer Object)** — объекты для передачи тестовых данных между слоями.
    - `UserTestData` — данные пользователя (имя, возраст, деньги и т.д.)
    - `CarTestData` — данные автомобиля (марка, модель, цена)

**Структура проекта:**

| Путь | Описание |
|------|----------|
| `src/main/java/api/` | API адаптеры (UserAdapter, CarAdapter, HouseAdapter) |
| `src/main/java/ui/pages/` | Page Object классы |
| `src/main/java/ui/dto/` | DTO объекты (UserTestData, CarTestData) |
| `src/main/java/ui/dto/factory/` | Data Factory классы |
| `src/main/java/ui/enumUI/` | Enum константы для UI |
| `src/main/java/ui/steps/` | Steps классы (базовые шаги) |
| `src/main/java/db/` | Подключение к БД (DBConnection) |
| `src/test/java/tests/api/cars/` | API тесты автомобилей |
| `src/test/java/tests/api/houses/` | API тесты домов |
| `src/test/java/tests/api/users/` | API тесты пользователей |
| `src/test/java/tests/db/` | DB тесты |
| `src/test/java/tests/ui/base/` | BaseTest |
| `src/test/java/tests/ui/login/` | Тесты авторизации |
| `src/test/java/tests/ui/users/` | UI тесты пользователей |
| `src/test/java/tests/ui/cars/` | UI тесты автомобилей |
| `src/test/java/tests/ui/houses/` | UI тесты домов |
| `src/test/java/tests/ui/allpost/` | Тесты страницы ALL POST |

## 🚀 Запуск тестов

### Запуск всех тестов
```bash
mvn clean test
```

### Запуск тестов с выбором браузера
```bash
mvn test -DBrowser=chrome
mvn test -DBrowser=firefox
mvn test -DBrowser=edge
```

> ⚠️ Чувствительные данные (логин, пароль) **не должны** храниться в коде.

---

## 📊 Allure-отчет

### Сформировать и открыть отчет
```bash
allure serve
```

Отчет откроется автоматически в браузере.

### Что отображается в отчете:
- Результаты выполнения тестов (passed / failed / skipped)
- Логи ошибок и скриншоты (для UI тестов)
- Шаги тестов (благодаря аннотациям `@Description`)
- Группировка по Epic и Feature

---

## 🔄 Git Workflow

### 1. Начало работы
```bash
git checkout master
git pull
```

### 2. Создание новой ветки
```bash
git checkout -b feature/your-feature-name
```

### 3. Внесение изменений
```bash
git add .
git commit -m "feat: add new test"
```

### 4. Push и создание PR
```bash
git push origin feature/your-feature-name
```

### 5. Code Review
- Получите аппрув от коллег
- Исправьте замечания (если есть)
- Вмерджьте PR в master
- Удалите ветку

---

## 👥 Команда и распределение задач

| Участник              | Зона ответственности                                                          |
|-----------------------|-------------------------------------------------------------------------------|
| Кадырмятова Анастасия | Cars: CRUD API, buy/sell, GET spec с токеном                                  |
| Лазарев Георгий       | Users: CRUD API, сортировка Read All, проверка user-cars                      |
| Хвадина Александра    | Houses: CRUD API, UI-тесты (create, read all, read by ID)                     |
| Квасникова Ольга      |                                                                               |
| Кирсанов Антон        |                                                                               |
| Макаров Дмитрий       | Настройка проекта и CI/CD; Issue a Loan (UI + API); валидация input (stepper) |

---

## 🎯 Основные E2E сценарии

Сквозные сценарии, проверяющие полные бизнес-процессы:

| ID | Сценарий | Описание | Тест |
|----|----------|----------|------|
| E2E-01 | Покупка автомобиля | Создать пользователя → Создать автомобиль → Купить автомобиль | `AllPostTest.testBuySellCar` |
| E2E-02 | Продажа автомобиля | Создать пользователя → Создать автомобиль → Продать автомобиль | `AllPostTest.testBuySellCar` |
| E2E-03 | Заселение в дом | Создать пользователя → Создать дом → Заселить пользователя | `AllPostTest.testSettleEvictUser` |
| E2E-04 | Выселение из дома | Создать пользователя → Создать дом → Заселить → Выселить | `AllPostTest.testSettleEvictUser` |

---

## 📝 Правила написания тестов

### ✅ DO (Делать)

- Используйте **DataProvider** с String значениями:
  ```java
  @DataProvider
  public Object[][] testData() {
      return new Object[][] {
          {String.valueOf(1000), "Toyota"},
          {String.valueOf(2000), "BMW"}
      };
  }
  ```

- Используйте **@BeforeMethod** для авторизации:
  ```java
  @BeforeMethod
  public void setUp() {
      loginPage.authorization();
  }
  ```

- Используйте **DataFaker/JavaFaker** для генерации данных:
  ```java
  String name = faker.name().fullName();
  String email = faker.internet().emailAddress();
  ```

- Очищайте данные после тестов:
  ```java
  @AfterMethod
  public void tearDown() {
      houseAdapter.deleteHouse(createdHouseId);
  }
  ```

### ❌ DON'T (Не делать)

- ❌ Не хардкодить чувствительные данные
- ❌ Не использовать избыточные комментарии
- ❌ Не забывть чистить за собой в тестах
- ❌ Не использовать неправильные сообщения об ошибках

---

## 🔒 Требования к безопасности

В проекте нельзя хранить чувствительные данные напрямую в коде.

К таким данным относятся:
- Логин приложения
- Пароль приложения
- URL БД
- Логин БД
- Пароль БД

Для CI используются **Jenkins credentials**.

---

<div align="center">

**Made by PFLB Team 2**

</div>

