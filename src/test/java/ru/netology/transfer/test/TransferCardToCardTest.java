package ru.netology.transfer.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.transfer.data.DataUser;
import ru.netology.transfer.page.DashboardPage;
import ru.netology.transfer.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferCardToCardTest {
    DashboardPage dashboardPage;
    DataUser.CardInfo firstCardInfo;
    DataUser.CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;


    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var userUnfo = DataUser.getUserUnfo();
        var verificationPage = loginPage.validLogin(userUnfo);
        var verificationCode = DataUser.getUserUnfo().getVerification();
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = DataUser.getFirstCardInfo();
        secondCardInfo = DataUser.getSecondCardInfo();
        firstCardBalance = dashboardPage.getcardBalance(firstCardInfo);
        secondCardBalance = dashboardPage.getcardBalance(secondCardInfo);

    }

    @Test
    public void shouldTransferFromFirstToSecond() {
        var amount = DataUser.generateValidAmount(firstCardBalance);
        var expectedBalanceFirtCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        dashboardPage.reloadDashboardPage();
        var actualBalanceFirstCard = dashboardPage.getcardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getcardBalance(secondCardInfo);
        assertAll(() -> assertEquals(expectedBalanceFirtCard, actualBalanceFirstCard),
                () -> assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard));
    }

    @Test
    public void shouldGetErrorMessageIdAmountMoreBalance() {
        var amount = DataUser.generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.makeValidTransfer(String.valueOf(amount), secondCardInfo);
        assertAll(() -> transferPage.findErrrorMessage("Выполена попытка перевода суммы, превышающей остаток на карте списания"),
                () -> dashboardPage.reloadDashboardPage(),
                () -> assertEquals(firstCardBalance, dashboardPage.getcardBalance(firstCardInfo)),
                () -> assertEquals(secondCardBalance, dashboardPage.getcardBalance(secondCardInfo)));

    }

}
