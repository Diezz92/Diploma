package ru.netology.web.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@AllArgsConstructor
public class DataHelper {

    @Value
    @AllArgsConstructor
    public static class PayInfo {
        private String cardNumber;
        private String cardMonth;
        private String cardYear;
        private String user;
        private String cvvCode;
    }

    public static PayInfo getSuccessPayData() {
        return new PayInfo("4444 4444 4444 4441", "07", "23", "Stanislav Kudriavtsev", "123");
    }

    public static PayInfo getFailurePayData() {
        return new PayInfo("4444 4444 4444 4442", "07", "23", "Stanislav Kudriavtsev", "123");
    }

    public static PayInfo getInvalidMonthPayDataSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "66", "23", "Stanislav Kudriavtsev", "123");
    }

    public static PayInfo getInvalidMonthPayDataLess01SuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "00", "23", "Stanislav Kudriavtsev", "123");
    }

    public static PayInfo getInvalidYearPayDataSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "99", "Stanislav Kudriavtsev", "123");
    }

    public static PayInfo getInvalidYearPayDataLessSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "10", "Stanislav Kudriavtsev", "123");
    }

    public static PayInfo getInvalidYearPayDataLess01SuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "00", "Stanislav Kudriavtsev", "123");
    }

    public static PayInfo getInvalidUserPayDataSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "23", "Станислав", "123");
    }

    public static PayInfo getInvalidUserPayDataRandomCharactersSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "23", "X Æ A-12", "123");
    }

    public static PayInfo getInvalidCvvCodePayDataSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "23", "Stanislav Kudriavtsev", "1");
    }

    public static PayInfo getInvalidCvvCodePayDataLess001SuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "23", "Stanislav Kudriavtsev", "000");
    }

    public static PayInfo getEmptyValueMonthPayDataSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "", "23", "Stanislav Kudriavtsev", "000");
    }

    public static PayInfo getEmptyValueYearPayDataSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "", "Stanislav Kudriavtsev", "000");
    }

    public static PayInfo getEmptyValueUserPayDataSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "23", "", "000");
    }

    public static PayInfo getEmptyValueCvvCodePayDataSuccessCard() {
        return new PayInfo("4444 4444 4444 4441", "07", "23", "Stanislav Kudriavtsev", "");
    }
}
