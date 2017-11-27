package tests;

import com.sun.org.glassfish.gmbal.Description;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by a.oreshnikova on 25.11.17.
 */


@Description("Проверка работы датчика температур")
@RunWith(DataProviderRunner.class)

public class InfoAboutStatusWaterTest {

    @BeforeClass
    public static void setBaseUrl() {
        RestAssured.baseURI = "http://ntanygin.pythonanywhere.com";
    }

    //Шкала градусов по Фаренгейту

    @DataProvider
    public static Object[][] iceTemp() {
        return new Object[][] {
                {-454},
                {-1},
                {0},
                {1},
                {31},
                {32},
        };
    }

    @Description("Состояние воды в диапазоне температур: от замерзания воды 32'F до -454'F (темпетарура в космосе)")
    @Test
    @UseDataProvider("iceTemp")
    public void negativeTemperature_Test (Integer temperature) {
        getStatus(temperature, "Ice");
    }

    @DataProvider
    public static Object[][] waterTemp() {
        return new Object[][] {
                {33},
                {34},
                {70},
                {210},
                {211},
        };
    }

    @Description("Состояние воды в диапазоне температур: от 33'F и до границы точки кипения 211'F")
    @Test
    @UseDataProvider("waterTemp")
    public void positiveTemperature_Test(Integer temperature) {
        getStatus(temperature, "Water");
    }

    @DataProvider
    public static Object[][] steamTemp() {
        return new Object[][] {
                {212},
                {213},
                {419},
                {999},
        };
    }

    @Description("Состояние воды в диапазоне температур: от точки кипения 212'F")
    @Test
    @UseDataProvider("steamTemp")
    public void steamTemperature_Test(Integer temperature) {
        getStatus(temperature, "Steam");
    }

    @DataProvider
    public static Object[][] doubleTypeTemp() {
        return new Object[][] {
                {-454.01, "Ice"},
                {31.5, "Ice"},
                {32.00, "Ice"},
                {33.9, "Water"},
                {210.09, "Water"},
                {212.01, "Steam"},
                {310.2, "Steam"},
        };
    }

    //При таком формате запроса возвращается код 400 и нет информации о состоянии воды
    @Description("Состояние воды при вводе значений с плавающей точкой от -454.00 до 214.00")
    @Test
    @UseDataProvider("doubleTypeTemp")
    public void statusTempIfDoubleFormat_Test(Double temperature, String status) {
        Response response = given().when().get("/?temperature=" + temperature);
        response.prettyPrint();
        assertThat(response.getStatusCode())
                .as("Код ошибки возращается не верно")
                .isEqualTo(200);
        assertThat(response.jsonPath().getString("state"))
                .as("Статус о состоянии воды при температуре = " + temperature + "'F НЕ верен")
                .isEqualTo(status);
    }

    //Получить статус о состоянии воды при указанной температуре
    private void getStatus(Integer temperature, String status) {
         Response response = given().when().get("/?temperature=" + temperature);
         response.prettyPrint();
         assertThat(response.getStatusCode())
                 .as("Код ошибки возращается не верно")
                 .isEqualTo(200);
         assertThat(response.jsonPath().getString("state"))
                 .as("Статус о состоянии воды при температуре = " + temperature + "'F НЕ верен")
                 .isEqualTo(status);
    }
}
