package rest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by a.oreshnikova on 28.11.2017.
 */

public class RestHelper<T> {

    //Получить статус о состоянии воды при указанной температуре
    public void getStatusAndAssert(String temperature, String status, int code) {
        Response response = given().when().get("/?temperature=" + temperature);
        response.prettyPrint();
        assertStatusCode(response, code);
        assertStatusWater(temperature, status, response);
    }

    //Вспомогательные методы
    private void assertStatusCode(Response response, int code) {
        assertThat(response.getStatusCode())
                .as("Код ошибки возращается не верно")
                .isEqualTo(code);
    }

    private void assertStatusWater(String temperature, String status, Response response) {
        if (response.getStatusCode() == 200) {
            assertThat(response.jsonPath().getString("state"))
                    .as("Статус о состоянии воды при температуре = " + temperature + "'F НЕ верен")
                    .isEqualTo(status);
        } else if (response.getStatusCode() == 400) {
            assertThat(response.jsonPath().getString("\"Oops!\""))
                    .as("Статус о состоянии воды при температуре = " + temperature + "'F НЕ верен")
                    .isEqualTo(status);
        }
    }
}
