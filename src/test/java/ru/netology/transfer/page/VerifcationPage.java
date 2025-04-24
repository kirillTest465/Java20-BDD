package ru.netology.transfer.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.transfer.data.DataUser;

import static com.codeborne.selenide.Selenide.$;

public class VerifcationPage {
    private final SelenideElement codeFied = $("[data-test-id=code] input");
    private final SelenideElement verifeButton = $("[data-test-id=action-verify]");

    public VerifcationPage() {
        codeFied.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        codeFied.setValue(DataUser.getUserUnfo().getVerification());
        verifeButton.click();
        return new DashboardPage();
    }
}
