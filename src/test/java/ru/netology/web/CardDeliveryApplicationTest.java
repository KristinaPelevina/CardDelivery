package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;

@Data
public class CardDeliveryApplicationTest {
    private SelenideElement form;
    String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        form = $("[action]");
    }

    @Nested
    public class FullyValid {

        @Test
        void shouldSubmitRequest() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(date));
        }

        @Test
        void shouldSubmitRequestIfNameWithHyphen() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Анна-Мария Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(date));
        }

        @Test
        void shouldSubmitRequestIfCityWithHyphen() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Улан-Удэ");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(date));
        }
    }

    @Nested
    public class CityTests {

        @Test
        void shouldNotSubmitRequestIfCityIsEmpty() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfCityIncorrect() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Лондон");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfCityInLatin() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Omsk");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class NameTests {

        @Test
        void shouldNotSubmitRequestIfNameIsEmpty() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }


        @Test
        void shouldNotSubmitRequestIfNameInLatin() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Rhbcnbyf Gtktdbyf");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfOnlyName() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfNameWithSymbols() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("--------------------");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class TelephoneTests {

        @Test
        void shouldNotSubmitRequestIfTelephoneIsEmpty() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfTelephone12Symbols() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+790619758822");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfTelephone10Symbols() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+7906197582");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfTelephoneWithoutPlus() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("7906197582");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfTelephoneWithSymbols() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+?():!(?%**");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class AgreeTests {

        @Test
        void shouldNotSubmitRequestWithoutAgreement() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(byText("Забронировать")).click();
            $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки"));
        }
    }

    @Nested
    public class InputDateTests {

        @Test
        void shouldNotSubmitRequestIfDateIsEmpty() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Екатеринбург");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys("");
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Неверно введена дата")).waitUntil(Condition.visible, 15000);
        }
    }
}