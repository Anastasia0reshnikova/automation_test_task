package rest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by a.oreshnikova on 28.11.2017.
 */

public class RestHelper<T> {

    //Получить статус о состоянии воды при указанной температуре
    public void getStatusAndAssert(Integer temperature, String status) {
        Response response = given().when().get("/?temperature=" + temperature);
        response.prettyPrint();
        assertStatusCode(response, 200);
        assertStatusWater(temperature, status, response);
    }

    public void getStatusAndAssert(Double temperature, String status) {
        Response response = given().when().get("/?temperature=" + temperature);
        response.prettyPrint();
        assertStatusCode(response, 200);
        assertStatusWater(temperature, status, response);
    }

    //Проверка негативных сценариев
    public void getStatus400AndAssert(T temperature) {
        Response response = given().when().get("/?temperature=" + temperature);
        response.prettyPrint();
        assertStatusCode(response, 400);
        assertNotTempData(temperature, response);
    }

    //Вспомогательные методы
    private void assertStatusCode(Response response, int code) {
        assertThat(response.getStatusCode())
                .as("Код ошибки возращается не верно")
                .isEqualTo(code);
    }

    private void assertStatusWater(Integer temperature, String status, Response response) {
        assertThat(response.jsonPath().getString("state"))
                .as("Статус о состоянии воды при температуре = " + temperature + "'F НЕ верен")
                .isEqualTo(status);
    }

    private void assertStatusWater(Double temperature, String status, Response response) {
        assertThat(response.jsonPath().getString("state"))
                .as("Статус о состоянии воды при температуре = " + temperature + "'F НЕ верен")
                .isEqualTo(status);
    }

    private void assertNotTempData(T temperature, Response response) {
        assertThat(response.jsonPath().getString("\"Oops!\""))
                .as("Статус о состоянии воды при температуре = " + temperature + "'F НЕ верен")
                .isEqualTo("It doesn't feel like temperature. Are you using Farengheit scale?");
    }
}
