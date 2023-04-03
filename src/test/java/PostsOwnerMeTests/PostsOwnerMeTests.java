package PostsOwnerMeTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostsOwnerMeTests {
    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @DisplayName("Проверка получения своих постов с сортировкой ASC")
    @Test
    void getPostsSortAscPositive(){
        JsonPath response = given()
                .header("X-Auth-Token", "0ff708e102396f8173e57a463460b47e")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "0")
                .expect()
                .statusCode(200)
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .body()
                .jsonPath();
        assertThat(response.get("data[0].id"), equalTo(15905));
        assertThat(response.get("data[0].title"), equalTo("4пку"));
    }

    @DisplayName("Проверка получения своих постов с сортировкой DESC")
    @Test
    void getPostsSortDescPositive(){
        JsonPath response = given()
                .header("X-Auth-Token", "0ff708e102396f8173e57a463460b47e")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "0")
                .expect()
                .statusCode(200)
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .body()
                .jsonPath();
        assertThat(response.get("data[0].id"), equalTo(16005));
        assertThat(response.get("data[0].title"), equalTo("Новый"));
    }

    @DisplayName("Проверка получения своих постов без авторизации")
    @Test
    void getPostsNegative(){
        given()
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "0")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .then()
                .statusCode(401);
    }

    @DisplayName("Проверка получения своих постов с невалидным токеном")
    @Test
    void getPostsTokenNegative(){
        given()
                .header("X-Auth-Token", "0ff708e102396f8173e57a463460b48e")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "0")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .then()
                .statusCode(401);
    }

    @DisplayName("Проверка получения списка постов несуществующей страницы")
    @Test
    void getPostsNoPagesNegative(){
        JsonPath response = given()
                .header("X-Auth-Token", "0ff708e102396f8173e57a463460b47e")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "1000")
                .expect()
                .statusCode(200)
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .body()
                .jsonPath();
        assertThat(response.get("data[0]")  ,  equalTo(null));

    }



}
