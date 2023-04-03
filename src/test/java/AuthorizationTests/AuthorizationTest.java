package AuthorizationTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthorizationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @DisplayName("Проверка авторизации с валидным логином и паролем")
    @Test
    void postAuthorizationPositive(){
        JsonPath response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("username", "tiuleneva")
                .formParam("password", "b902d8ce62")
                .expect()
                .statusCode(200)
                .when()
                .post("https://test-stand.gb.ru/gateway/login")
                .body()
                .jsonPath();
        assertThat(response.get("id"), equalTo(6033));
    }

    @DisplayName("Проверка авторизации без логина и пароля")
    @Test
    void postAuthorizationNullNegative(){
        JsonPath response = given()
                .expect()
                .statusCode(400)
                .when()
                .post("https://test-stand.gb.ru/gateway/login")
                .body()
                .jsonPath();
        assertThat(response.get("message"), equalTo("The key \"username\" must be provided."));
    }

    @DisplayName("Проверка авторизации с несуществующим логином и паролем")
    @Test
    void postAuthorizationNegative(){
        JsonPath response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("username", "tiu")
                .formParam("password", "b902d")
                .expect()
                .statusCode(401)
                .when()
                .post("https://test-stand.gb.ru/gateway/login")
                .body()
                .jsonPath();
        assertThat(response.get("error"), equalTo("Invalid credentials."));
    }

}
