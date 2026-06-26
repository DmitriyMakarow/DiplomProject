package tests.api.houses;

import api.models.HouseRequest;
import api.models.HouseResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static ui.pages.base.BasePage.faker;

@Epic("Дома. API")
@Feature("Создание и редактирование дома")
public class HouseApiTest extends BaseTest {

    private final int floorCount = faker.number().numberBetween(1, 50);
    private final double price = faker.number().numberBetween(100000, 10000000);
    private final int parkingCount = faker.number().numberBetween(1, 10);

    HouseRequest.ParkingPlace parkingPlace = HouseRequest.ParkingPlace.builder()
            .isWarm(true)
            .isCovered(true)
            .placesCount(parkingCount)
            .build();

    HouseRequest house = HouseRequest.builder()
            .floorCount(floorCount)
            .price(price)
            .parkingPlaces(Collections.singletonList(parkingPlace))
            .lodgers(Collections.emptyList())
            .build();

    @Test(testName = "Проверка создания дома с валидными параметрами")
    void checkCreateHouse() {
        HouseResponse houseResponse = houseAdapter.createApiHouse(house);
        Integer idHouse = houseResponse.getId();

        assertEquals(houseResponse.getFloorCount(), floorCount, "Количество этажей не совпадает");
        assertEquals(houseResponse.getPrice(), price, "Цена не совпадает");

        houseAdapter.deleteApiHouse(idHouse);
    }

    @Test(testName = "Проверка редактирования дома валидными параметрами")
    void checkEditHouse() {
        int newFloorCount = faker.number().numberBetween(1, 50);
        double newPrice = faker.number().numberBetween(100000, 10000000);
        int newParkingCount = faker.number().numberBetween(1, 10);

        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        Integer idHouse = createdHouse.getId();

        HouseRequest.ParkingPlace newParkingPlace = HouseRequest.ParkingPlace.builder()
                .isWarm(false)
                .isCovered(true)
                .placesCount(newParkingCount)
                .build();

        HouseRequest updatedHouse = HouseRequest.builder()
                .floorCount(newFloorCount)
                .price(newPrice)
                .parkingPlaces(Collections.singletonList(newParkingPlace))
                .lodgers(Collections.emptyList())
                .build();

        HouseResponse updatedResponse = houseAdapter.putApiHouse(idHouse, updatedHouse);

        assertEquals(updatedResponse.getFloorCount(), newFloorCount, "Количество этажей не совпадает после обновления");
        assertEquals(updatedResponse.getPrice(), newPrice, "Цена не совпадает после обновления");
        assertEquals(updatedResponse.getParkingPlaces().get(0).getPlacesCount(), newParkingCount, "Количество парковочных мест не совпадает после обновления");

        houseAdapter.deleteApiHouse(idHouse);
    }
}