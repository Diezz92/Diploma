package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;
import ru.netology.web.page.StartPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiplomaTest {

    StartPage startPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @BeforeEach
    void setUp() {
        startPage = open("http://localhost:8080", StartPage.class);
        SQLHelper.dropTables();
        SQLHelper.createTablePaymentGate();
        SQLHelper.createTableCreditGate();
    }

    // Тесты на Payment Gate
    @Test
    @DisplayName("1.1 Заполнение валидными данными, Payment Gate (позитивный сценарий)")
    void shouldSuccessPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getSuccessPayData();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.successMessage("Операция одобрена Банком.");
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("2.1 Заполнение валидными данными, Payment Gate (негативный сценарий)")
    void shouldFailPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getFailurePayData();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureMessage("Банк отказал в проведении операции");
        SQLHelper.insertDeclinedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("3.1 заполнение поля Месяц невалидным значением, Payment Gate (негативный сценарий)")
    void shouldFailMonthMore12SuccessCardPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getInvalidMonthPayDataSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureDates();
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("3.2 заполнение поля Месяц невалидным значением (00), Payment Gate (негативный сценарий)")
    void shouldFailMonthLess01SuccessCardPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getInvalidMonthPayDataLess01SuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureDates();
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("4.1 заполнение поля Год невалидным значением, Payment Gate (негативный сценарий)")
    void shouldFailYearMoreSuccessCardPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getInvalidYearPayDataSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureDates();
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("4.2 заполнение поля Год невалидным значением (завершившийся год), Payment Gate (негативный сценарий)")
    void shouldFailYearLessSuccessCardPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getInvalidYearPayDataLessSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureDatesLess();
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("4.3 заполнение поля Год невалидным значением (00), Payment Gate (негативный сценарий)")
    void shouldFailYearLess01SuccessCardPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getInvalidYearPayDataLess01SuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureDatesLess();
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("5.1 заполнение поля Владелец невалидным значением, Payment Gate (негативный сценарий)")
    void shouldFailUserSuccessCardPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getInvalidUserPayDataSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureUser();
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("5.2 заполнение поля Владелец невалидным значением (случайными символами), Payment Gate (негативный сценарий)")
    void shouldFailUserRandomCharactersSuccessCardPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getInvalidUserPayDataRandomCharactersSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureUser();
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("6.1 заполнение поля CVC/CVV невалидным значением, Payment Gate (негативный сценарий)")
    void shouldFailCVVSuccessCardPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getInvalidCvvCodePayDataSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("6.2 заполнение поля CVC/CVV невалидным значением (000), Payment Gate (негативный сценарий)")
    void shouldFailCVVEqualTo000SuccessCardPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getInvalidCvvCodePayDataLess001SuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertApprovedCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("7.1 Работа с пустыми полями, Payment Gate (негативный сценарий)")
    void shouldFailEmptyFieldsPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        paymentGatePage.emptyClick();
        paymentGatePage.failureEmptyFields();
        SQLHelper.insertEmptyNoCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("7.2 заполнение всех полей, кроме поля Месяц, Payment Gate (негативный сценарий)")
    void shouldFailEmptyFieldsExceptForTheMonthPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getEmptyValueMonthPayDataSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertEmptyPaymentGateApprovedCard();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("7.3 заполнение всех полей, кроме поля Год, Payment Gate (негативный сценарий)")
    void shouldFailEmptyFieldsExceptForTheYearPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getEmptyValueYearPayDataSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertEmptyPaymentGateApprovedCard();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("7.4 заполнение всех полей, кроме поля Владелец, Payment Gate (негативный сценарий)")
    void shouldFailEmptyFieldsExceptForTheUserPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getEmptyValueUserPayDataSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureUser();
        SQLHelper.insertEmptyPaymentGateApprovedCard();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }

    @Test
    @DisplayName("7.5 заполнение всех полей, кроме поля CVC/CVV, Payment Gate (негативный сценарий)")
    void shouldFailEmptyFieldsExceptForTheCVVPaymentGate() {
        val paymentGatePage = startPage.paymentGate();
        val payInfo = DataHelper.getEmptyValueCvvCodePayDataSuccessCard();
        paymentGatePage.CardData(payInfo);
        paymentGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertEmptyPaymentGateApprovedCard();
        assertEquals("", SQLHelper.getCardStatusPaymentGate(SQLHelper.getPayTable()));
    }


    // Тесты на Credit Gate


    @Test
    @DisplayName("1.1 Заполнение валидными данными, Credit Gate (позитивный сценарий)")
    void shouldSuccessCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getSuccessPayData();
        creditGatePage.CardData(payInfo);
        creditGatePage.successMessage("Операция одобрена Банком.");
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }


    @Test
    @DisplayName("2.1 Заполнение валидными данными, Credit Gate (негативный сценарий)")
    void shouldFailCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getFailurePayData();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureMessage("Банк отказал в проведении операции");
        SQLHelper.insertDeclinedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }


    @Test
    @DisplayName("3.1 заполнение поля Месяц невалидным значением, Credit Gate (негативный сценарий)")
    void shouldFailMonthMore12SuccessCardCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getInvalidMonthPayDataSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureDates();
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }


    @Test
    @DisplayName("3.2 заполнение поля Месяц невалидным значением (00), Credit Gate (негативный сценарий)")
    void shouldFailMonthLess01SuccessCardCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getInvalidMonthPayDataLess01SuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureDates();
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }


    @Test
    @DisplayName("4.1 заполнение поля Год невалидным значением, Credit Gate (негативный сценарий)")
    void shouldFailYearMoreSuccessCardCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getInvalidYearPayDataSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureDates();
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }

    @Test
    @DisplayName("4.2 заполнение поля Год невалидным значением (завершившийся год), Credit Gate (негативный сценарий)")
    void shouldFailYearLessSuccessCardCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getInvalidYearPayDataLessSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureDatesLess();
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }

    @Test
    @DisplayName("4.3 заполнение поля Год невалидным значением (00), Credit Gate (негативный сценарий)")
    void shouldFailYearLess01SuccessCardCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getInvalidYearPayDataLess01SuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureDatesLess();
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }


    @Test
    @DisplayName("5.1 заполнение поля Владелец невалидным значением, Credit Gate (негативный сценарий)")
    void shouldFailUserSuccessCardCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getInvalidUserPayDataSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureUser();
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }

    @Test
    @DisplayName("5.2 заполнение поля Владелец невалидным значением (случайными символами), Credit Gate (негативный сценарий)")
    void shouldFailUserRandomCharactersSuccessCardCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getInvalidUserPayDataRandomCharactersSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureUser();
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }


    @Test
    @DisplayName("6.1 заполнение поля CVC/CVV невалидным значением, Credit Gate (негативный сценарий)")
    void shouldFailCVVSuccessCardCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getInvalidCvvCodePayDataSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }

    @Test
    @DisplayName("6.2 заполнение поля CVC/CVV невалидным значением (000), Credit Gate (негативный сценарий)")
    void shouldFailCVVEqualTo000SuccessCardCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getInvalidCvvCodePayDataLess001SuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertApprovedCardCreditGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }

    @Test
    @DisplayName("7.1 Работа с пустыми полями, Credit Gate (негативный сценарий)")
    void shouldFailEmptyFieldsCreditGate() {
        val creditGatePage = startPage.creditGate();
        creditGatePage.emptyClick();
        creditGatePage.failureEmptyFields();
        SQLHelper.insertEmptyNoCardPaymentGate();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }

    @Test
    @DisplayName("7.2 заполнение всех полей, кроме поля Месяц, Credit Gate (негативный сценарий)")
    void shouldFailEmptyFieldsExceptForTheMonthCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getEmptyValueMonthPayDataSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertEmptyCreditGateApprovedCard();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }

    @Test
    @DisplayName("7.3 заполнение всех полей, кроме поля Год, Credit Gate (негативный сценарий)")
    void shouldFailEmptyFieldsExceptForTheYearCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getEmptyValueYearPayDataSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertEmptyCreditGateApprovedCard();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }

    @Test
    @DisplayName("7.4 заполнение всех полей, кроме поля Владелец, Credit Gate (негативный сценарий)")
    void shouldFailEmptyFieldsExceptForTheUserCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getEmptyValueUserPayDataSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureUser();
        SQLHelper.insertEmptyCreditGateApprovedCard();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }

    @Test
    @DisplayName("7.5 заполнение всех полей, кроме поля CVC/CVV, Credit Gate (негативный сценарий)")
    void shouldFailEmptyFieldsExceptForTheCVVCreditGate() {
        val creditGatePage = startPage.creditGate();
        val payInfo = DataHelper.getEmptyValueCvvCodePayDataSuccessCard();
        creditGatePage.CardData(payInfo);
        creditGatePage.failureMonthOrYearOrCvvCode();
        SQLHelper.insertEmptyCreditGateApprovedCard();
        assertEquals("", SQLHelper.getCardStatusCreditGate(SQLHelper.getCreditTable()));
    }
}
