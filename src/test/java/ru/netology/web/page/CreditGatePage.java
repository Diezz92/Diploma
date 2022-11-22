package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
public class CreditGatePage {

    private SelenideElement heading = $(byText("Кредит по данным карты"));
    private SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("input[placeholder='08']");
    private SelenideElement yearField = $("input[placeholder='22']");
    private ElementsCollection userField = $$(".input__control");
    private SelenideElement cardUser = userField.get(3);
    private SelenideElement cvvField = $("input[placeholder='999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement success = $(byText("Операция одобрена Банком."));
    private SelenideElement failure = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement invalidDates = $(byText("Неверно указан срок действия карты"));
    private SelenideElement invalidDatesLess = $(byText("Истёк срок действия карты"));
    private SelenideElement invalidParameters = $(byText("Неверный формат"));
    private SelenideElement emptyFields = $(byText("Поле обязательно для заполнения"));

    public CreditGatePage() {
        heading.shouldBe(visible);
    }

    public void CardData (DataHelper.PayInfo info) {
        cardNumberField.setValue(info.getCardNumber());
        monthField.setValue(info.getCardMonth());
        yearField.setValue(info.getCardYear());
        cardUser.setValue(info.getUser());
        cvvField.setValue(info.getCvvCode());
        continueButton.click();
    }

    public void successMessage(String expectedText) {
        success.shouldHave(exactText(expectedText), Duration.ofSeconds(10)).shouldBe(visible);
    }

    public void failureMessage(String expectedText) {
        failure.shouldHave(exactText(expectedText), Duration.ofSeconds(10)).shouldBe(visible);
    }

    public void failureDates() {
        invalidDates.shouldBe(visible);
    }

    public void failureDatesLess() {
        invalidDatesLess.shouldBe(visible);
    }

    public void failureMonthOrYearOrCvvCode() {
        invalidParameters.shouldBe(visible);
    }

    public void failureUser() {
        emptyFields.shouldBe(visible);
    }

    public void emptyClick() {
        continueButton.click();
    }

    public void failureEmptyFields() {
        invalidParameters.shouldBe(visible);
        emptyFields.shouldBe(visible);
    }
}
