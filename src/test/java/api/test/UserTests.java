package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.oneOf;
import static org.testng.Assert.assertEquals;

public class UserTests {
    Faker faker;
    User userPayload;

    @BeforeTest
    public void setupData() {
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
    }

    @Test(priority = 1)
    public void testPostUser() {
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, dependsOnMethods = "testPostUser")
    public void testGetUserByName() {
        Response response = UserEndPoints.readUser(this.userPayload.getUsername());
        response.then().log().all();
        assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    public void testUpdateUserByName() {

        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints.updateUser(userPayload, this.userPayload.getUsername());
        response.then().log().body();
        assertEquals(response.getStatusCode(), 200);

        System.out.println("Updated body below:");

        Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());
        responseAfterUpdate.then().log().body();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


    @Test(priority = 4)
    public void testDeleteUserByName() {

        Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
        System.out.println("Deleted user body below:");
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);


    }


}
