package PostsOwnerNotMeTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostsOwnerNotMeTest {

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DisplayName("Проверка получения чужик постов с сортировкой ASC")
    @Test
    void getPostsSortAscPositive(){
        JsonPath response = given()
                .header("X-Auth-Token", "0ff708e102396f8173e57a463460b47e")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "0")
                .expect()
                .statusCode(200)
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .body()
                .jsonPath();
        assertThat(response.get("data[0].id"), equalTo(1));
        assertThat(response.get("data[0].title"), equalTo("жареные сосиски"));
    }

    @DisplayName("Проверка получения чужик постов с сортировкой DESC")
    @Test
    void getPostsSortDescPositive(){
        given()
                .header("X-Auth-Token", "0ff708e102396f8173e57a463460b47e")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "0")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .then()
                .statusCode(200);
    }

    @DisplayName("Проверка получения чужик постов с сортировкой ALL")
    @Test
    void getPostsSortAllPositive(){
        given()
                .header("X-Auth-Token", "0ff708e102396f8173e57a463460b47e")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ALL")
                .queryParam("page", "0")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .then()
                .statusCode(200);
    }


    @DisplayName("Проверка получения чужик постов без авторизации")
    @Test
    void getPostsNegative(){
        given()
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ALL")
                .queryParam("page", "0")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .then()
                .statusCode(401);
    }

    @DisplayName("Проверка получения чужик постов с невалидным токеном")
    @Test
    void getPostsTokenNegative(){
        given()
                .header("X-Auth-Token", "0ff708e102396f8173e57a463460b48e")
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ALL")
                .queryParam("page", "0")
                .when()
                .get("https://test-stand.gb.ru/api/posts")
                .then()
                .statusCode(401);
    }






}
