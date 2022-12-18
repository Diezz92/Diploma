package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    @Value
    @AllArgsConstructor
    public static class PayInfo {
        private String cardNumber;
        private String cardMonth;
        private String cardYear;
        private String user;
        private String cvvCode;
    }

    public static String successCard = "4444 4444 4444 4441";

    public static String failedCard = "4444 4444 4444 4442";

    public static String getMonth(int minusMonth) {
        return LocalDate.now().minusMonths(minusMonth).format(DateTimeFormatter.ofPattern("MM"));
    }


    public static String getMonthInvalid() {
        return String.valueOf(faker.number().numberBetween(13, 99));
    }

    public static String setDate(int plusYear) {
        return LocalDate.now().plusYears(plusYear).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String setDateInvalid(int minusYear) {
        return LocalDate.now().minusYears(minusYear).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String setName() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String setCvv() {
        return faker.numerify("###");
    }

    public static PayInfo getSuccessPayData() {
        return new PayInfo(successCard, getMonth(1), setDate(1), setName(), setCvv());
    }

    public static PayInfo getFailurePayData() {
        return new PayInfo(failedCard, getMonth(1), setDate(1), setName(), setCvv());
    }

    public static PayInfo getInvalidMonthPayDataSuccessCard() {
        return new PayInfo(successCard, getMonthInvalid(), setDate(1), setName(), setCvv());
    }

    public static PayInfo getInvalidMonthPayDataLess01SuccessCard() {
        return new PayInfo(successCard, "00", setDate(1), setName(), setCvv());
    }

    public static PayInfo getInvalidYearPayDataSuccessCard() {
        return new PayInfo(successCard, getMonth(1), setDate(50), setName(), setCvv());
    }

    public static PayInfo getInvalidYearPayDataLessSuccessCard() {
        return new PayInfo(successCard, getMonth(1), setDateInvalid(5), setName(), setCvv());
    }

    public static PayInfo getInvalidYearPayDataLess01SuccessCard() {
        return new PayInfo(successCard, getMonth(1), "00", setName(), setCvv());
    }

    public static PayInfo getInvalidUserPayDataSuccessCard() {
        return new PayInfo(successCard, getMonth(1), setDate(1), "Станислав", setCvv());
    }

    public static PayInfo getInvalidUserPayDataRandomCharactersSuccessCard() {
        return new PayInfo(successCard, getMonth(1), setDate(1), "X Æ A-12", setCvv());
    }

    public static PayInfo getInvalidCvvCodePayDataSuccessCard() {
        return new PayInfo(successCard, getMonth(1), setDate(1), setName(), "1");
    }

    public static PayInfo getInvalidCvvCodePayDataLess001SuccessCard() {
        return new PayInfo(successCard, getMonth(1), setDate(1), setName(), "000");
    }

    public static PayInfo getEmptyValueMonthPayDataSuccessCard() {
        return new PayInfo(successCard, "", setDate(1), setName(), "000");
    }

    public static PayInfo getEmptyValueYearPayDataSuccessCard() {
        return new PayInfo(successCard, getMonth(1), "", setName(), "000");
    }

    public static PayInfo getEmptyValueUserPayDataSuccessCard() {
        return new PayInfo(successCard, getMonth(1), setDate(1), "", "000");
    }

    public static PayInfo getEmptyValueCvvCodePayDataSuccessCard() {
        return new PayInfo(successCard, getMonth(1), setDate(1), setName(), "");
    }
}
