package test.tests.api;

import api.models.HouseRequest;
import api.models.HouseResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import test.tests.BaseTest;

import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

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

    @Test(testName = "Проверка получения списка домов")
    void checkGetHouses() {
        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        Integer idHouse = createdHouse.getId();

        List<HouseResponse> housesResponse = houseAdapter.getHouses();

        HouseResponse foundHouse = housesResponse.stream()
                .filter(h -> h.getId() == idHouse)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Созданный дом не найден в списке"));

        assertEquals(foundHouse.getFloorCount(), floorCount, "Количество этажей не совпадает");
        assertEquals(foundHouse.getPrice(), price, "Цена не совпадает");

        houseAdapter.deleteApiHouse(idHouse);
    }

    @Test(testName = "Проверка получения дома по ID")
    void checkGetHouseById() {
        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        Integer idHouse = createdHouse.getId();

        HouseResponse houseResponse = houseAdapter.getHouse(idHouse);

        assertEquals(houseResponse.getId(), idHouse, "ID дома не совпадает");
        assertEquals(houseResponse.getFloorCount(), floorCount, "Количество этажей не совпадает");
        assertEquals(houseResponse.getPrice(), price, "Цена не совпадает");
        assertEquals(houseResponse.getParkingPlaces().get(0).getPlacesCount(), parkingCount, "Количество парковочных мест не совпадает");

        houseAdapter.deleteApiHouse(idHouse);
    }

    @Test(testName = "Проверка удаления дома")
    void checkDeleteHouse() {
        HouseResponse createdHouse = houseAdapter.createApiHouse(house);
        Integer idHouse = createdHouse.getId();

        houseAdapter.deleteApiHouse(idHouse);

        List<HouseResponse> housesAfterDelete = houseAdapter.getHouses();
        boolean houseExists = housesAfterDelete.stream()
                .anyMatch(h -> h.getId() == idHouse);

        assertFalse(houseExists, "Дом должен быть удалён из списка");
    }
}