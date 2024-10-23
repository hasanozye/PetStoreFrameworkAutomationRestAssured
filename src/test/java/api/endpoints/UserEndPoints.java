package api.endpoints;

//UserEndPoints.java
// Created for performing the Create, Read, Update, Delete requests the user API


import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static api.endpoints.Routes.*;
import static io.restassured.RestAssured.*;

public class UserEndPoints {

    public static Response createUser(User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(post_url);

        return response;
    }

    public static Response readUser(String username) {
        Response response = given()
                .pathParam("username", username)
                .when()
                .get(Routes.get_url);

        return response;
    }

    public static Response updateUser(User payload, String username) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", username)
                .body(payload)
                .when()
                .put(update_url);

        return response;
    }


    public static Response deleteUser(String username) {
        Response response = given()
                .pathParam("username", username)
                .when()
                .delete(delete_url);

        return response;

    }


}
