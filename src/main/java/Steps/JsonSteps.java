package Steps;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonSteps {


    @Step
    public List<String> searchForCommentsByUser(String username) {

        Response response = given()
                .contentType(ContentType.JSON)
                .param("username", username)
                .when()
                .get("/users")
                .then()
                .extract()
                .response();

        Response response1 = given()
                .contentType(ContentType.JSON)
                .param("userId", response.jsonPath().getString("id[0]"))
                .when()
                .get("/posts")
                .then()
                .extract()
                .response();

        Response response2 = given()
                .contentType(ContentType.JSON)
                .param("postId", response1.jsonPath().getList("id"))
                .when()
                .get("/comments")
                .then()
                .extract()
                .response();

        List<String> emails = response2.jsonPath().getList("email");

        return emails;
    }

    @Step
    public boolean checkIfEmailIsValid() {

        String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

        Pattern pattern = Pattern.compile(regex);

        for (String email : searchForCommentsByUser("Delphine")) {
            Matcher matcher = pattern.matcher(email);

            return matcher.matches();
        }
        return false;
    }
}

