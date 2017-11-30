package rest;

import com.sun.org.glassfish.gmbal.Description;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by a.oreshnikova on 25.11.17.
 */


@Description("Проверка работы датчика температур")
@RunWith(DataProviderRunner.class)

public class InfoAboutStatusWaterTest {

    private static RestHelper rest;

    @BeforeClass
    public static void setBaseUrl() {
        rest = new RestHelper();
        RestAssured.baseURI = "http://ntanygin.pythonanywhere.com";
    }

    //Шкала градусов по Фаренгейту

    @DataProvider
    public static Object[][] iceTemp() {
        return new Object[][] {
                {-454},
                {0},
                {32},
        };
    }

    @Description("Состояние воды в диапазоне температур: от замерзания воды 32'F до -454'F (темпетарура в космосе)")
    @Test
    @UseDataProvider("iceTemp")
    public void negativeTemperature_Test (Integer temperature) {
        rest.getStatusAndAssert(temperature.toString(), "Ice", 200);
    }

    @DataProvider
    public static Object[][] waterTemp() {
        return new Object[][] {
                {33},
                {211},
        };
    }

    @Description("Состояние воды в диапазоне температур: от 33'F и до границы точки кипения 211'F")
    @Test
    @UseDataProvider("waterTemp")
    public void positiveTemperature_Test(Integer temperature) {
        rest.getStatusAndAssert(temperature.toString(), "Water", 200);
    }

    @DataProvider
    public static Object[][] steamTemp() {
        return new Object[][] {
                {212},
                {300},
        };
    }

    @Description("Состояние воды в диапазоне температур: от точки кипения 212'F")
    @Test
    @UseDataProvider("steamTemp")
    public void steamTemperature_Test(Integer temperature) {
        rest.getStatusAndAssert(temperature.toString(), "Steam", 200);
    }

    @DataProvider
    public static Object[][] doubleTypeTemp() {
        return new Object[][] {
                {-454.01, "Ice"},
                {32.5, "Ice"},
                {64.0/2.0, "Ice"},
                {33.9, "Water"},
                {210.09, "Water"},
                {212.01, "Steam"},
        };
    }

    //При таком формате запроса возвращается код 400 и нет информации о состоянии воды
    @Description("Состояние воды при вводе значений с плавающей точкой от -454.00 до 214.00")
    @Test
    @UseDataProvider("doubleTypeTemp")
    public void statusTempIfDoubleFormat_Test(Double temperature, String status) {
        rest.getStatusAndAssert(temperature.toString(), status, 200);
    }

    @DataProvider
    public static Object[][] notCorrectTemp() {
        return new Object[][] {
                {"seven"}, //Строка
                {"null"},
                {"-590000900"}, //Большое отрицательное число
                {"@%^"}, //Символы
                {""}, //Пустая строка
        };
    }

    @Description("Реакция датчика при не корректных данных")
    @Test
    @UseDataProvider("notCorrectTemp")
    public void notCorrectTemp_Test(String temperature) {
        String status = "It doesn't feel like temperature. Are you using Farengheit scale?";
        rest.getStatusAndAssert(temperature, status, 400);
    }
}
