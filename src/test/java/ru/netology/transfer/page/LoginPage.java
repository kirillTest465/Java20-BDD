package ru.netology.transfer.page;

import ru.netology.transfer.data.DataUser;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public VerifcationPage validLogin(DataUser.UserUnfo unfo) {
        $("[data-test-id=login] input").setValue(unfo.getLogin());
        $("[data-test-id=password] input").setValue(unfo.getPassword());
        $("[data-test-id=action-login]").click();
        //  $("[data-test-id=code] input").setValue(unfo.getVerification());
        return new VerifcationPage();
//Нужно реализовать класс в котором будет логика авторизации на странице логина
    }
}
