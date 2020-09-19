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
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Омск");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
            form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(date));
        }
    }

    @Test
    void shouldSubmitRequestIfNameWithHyphen() {
        form.$(cssSelector("[data-test-id=city] input")).sendKeys("Омск");
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
        form.$(cssSelector("[data-test-id=city] input")).sendKeys("Петропавловск-Камчатский");
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
        form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
        form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(date));
    }

    @Test
    void shouldSubmitRequestIfDataValid() {
        form.$(cssSelector("[data-test-id=city] input")).sendKeys("Петропавловск-Камчатский");
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
        form.$(cssSelector("[name=name]")).sendKeys("Кристина Пелевина");
        form.$(cssSelector("[name=phone]")).sendKeys("+79061975882");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(date));
    }
}


