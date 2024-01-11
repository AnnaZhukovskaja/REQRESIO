
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresTests {
   //1
@Test
public void getListUsers() {
    given()
    .when()
            .get("https://reqres.in/api/users?page=2")
    .then()
            .log().all()
            .statusCode(200)
            .body("per_page", equalTo(6))
            .body("data[0].id", equalTo(7),
                    "data.id", hasItems(7,8,9,10,11,12));
}

    //2
    @Test
    public void getSingleUser() {
        given()
        .when()
                .get("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    //3
    @Test
    public void getSingleUserNotFound() {
        given()
        .when()
                .get("https://reqres.in/api/users/23")
        .then()
                .log().all()
                .statusCode(404);
    }

    //4
    @Test
    public void getListResource() {
        given()
        .when()
                .get("https://reqres.in/api/unknown")
        .then()
                .log().all()
                .statusCode(200)
                .body("data[4].pantone_value",equalTo("17-1456"));
    }

    //5
    @Test
    public void getSingleResource() {
        given()
        .when()
                .get("https://reqres.in/api/unknown/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("support.url",equalTo("https://reqres.in/#support-heading"));
    }

    //6
    @Test
    public void getSingleResourceNotFound() {
        given()
        .when()
                .get("https://reqres.in/api/unknown/23")
        .then()
                .log().all()
                .statusCode(404);
    }

    //7post
    @Test
    public void create() {
        given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .log().all()
                .statusCode(201);
    }

    //8
    @Test
    public void putUpdate() {
        String user = given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
        .when()
                .put("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().asString();
        System.out.println(user);
    }

    //9
    @Test
    public void patchUpdate() {
        given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
        .when()
                .patch("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200);
    }

    //10
    @Test
    public void delete() {
        given()
        .when()
                .delete("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(204);
    }

    //11
    @Test
    public void postRegisterSuccessful() {
        given()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .header("Content-Type", "application/json")
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().all()
                .statusCode(200);
    }

    //12
    @Test
    public void postRegisterUnsuccessful() {
        given()
                .body("{\n" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}")
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().all()
                .statusCode(400);
    }

    //13
    @Test
    public void postLoginSuccessful() {
        given()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .header("Content-Type", "application/json")
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().all()
                .statusCode(200);
    }

    //14
    @Test
    public void postLoginUnsuccessful() {
        given()
                .body("{\n" +
                        "    \"email\": \"peter@klaven\"\n" +
                        "}")
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().all()
                .statusCode(400);
    }

    //15 DELAYED RESPONSE
    @Test
    public void getDelayedResponse() {
        given()
        .when()
                .get("https://reqres.in/api/users?delay=3")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.id", hasSize(6));
    }
}
