package ru.netology.models;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static ru.netology.models.AuthTest.requestSpec;

public class JSONparts {

    private JSONparts() {
    }

    public static void jsonPartLogin(UserAPI user) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/auth") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String jsonPartCode(UserCode userCode) {
        String token =
                given() // "дано"
                        .spec(requestSpec) // указываем, какую спецификацию используем
                        .body(userCode) // передаём в теле объект, который будет преобразован в JSON
                        .when() // "когда"
                        .post("/api/auth/verification") // на какой путь, относительно BaseUri отправляем запрос
                        .then() // "тогда ожидаем"
                        .statusCode(200)// код 200 OK
                        .extract()
                        .path("token");
        return token;
    }

    public static void jsonPartListCard(String token) {
        Response response =
                given() // "дано"
                        .spec(requestSpec) // указываем, какую спецификацию используем
                        .headers(
                                "Authorization",
                                "Bearer " + token) // передаём token
                        .when() // "когда"
                        .get("/api/cards") // на какой путь, относительно BaseUri отправляем запрос
                        .then() // "тогда ожидаем"
                        .statusCode(200)
                        .extract()
                        .response(); // код 200 OK
    }

    public static void jsonPartTransfer(Transfer transfer, String token) {
        Response response =
                given() // "дано"
                        .spec(requestSpec) // указываем, какую спецификацию используем
                        .headers(
                                "Authorization",
                                "Bearer " + token)
                        .body(transfer) // передаём в теле объект, который будет преобразован в JSON
                        .when() // "когда"
                        .post("/api/transfer") // на какой путь, относительно BaseUri отправляем запрос
                        .then() // "тогда ожидаем"
                        .statusCode(200)
                        .extract()
                        .response();
    }
}
