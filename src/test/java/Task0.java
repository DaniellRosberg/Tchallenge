import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CreateUserModel;
import model.CreateUserResponseModel;
import model.JsonData;
import model.TestTempl;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.hasItems;
//tsk0
public class Task0 {

    final Logger logger = LoggerFactory.getLogger(Task0.class);
    private static final Integer[] idCollection = {1,2,3,4,5,6};
    private static final String[] emailCollection = {"george.bluth@reqres.in","emma.wong@reqres.in"};


    @Test
    public void validateGet(){
        RestAssured.get("https://reqres.in/api/users?page=1")
                .then()
                .body("data.id", hasItems(idCollection));

    }

    @Test
     public void jsonDeserializer(){
     TestTempl testTempl = RestAssured.get("https://reqres.in/api/users?page=1")
             .then()
                    .extract()
                    .body()
                    .as(TestTempl.class);
        logger.info("Request message {}", testTempl);
        for (JsonData jsonData :
                testTempl.getData()) {
            hasItems(emailCollection);
        }
    }

    @Test
    public void correctCreateUserTest() throws ParseException {
        CreateUserModel requestJsonStr2 = new CreateUserModel("ajhfgaj", "jahsdfj");
        RequestSpecification corrReq = RestAssured.given();
        corrReq.header("Content-Type", "application/json");
        corrReq.body(requestJsonStr2);
        Response correctResponse = corrReq.post("https://reqres.in/api/users");
        int actStatusCode = correctResponse.statusCode();
        Assert.assertEquals(201,actStatusCode);
        CreateUserResponseModel  createUserResponseModel = correctResponse.body().as(CreateUserResponseModel.class);
        SimpleDateFormat locDate =  new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = locDate.parse(createUserResponseModel.getCreatedAt());
        locDate =  new SimpleDateFormat("dd.MM.yyyy");
        logger.info("CreatedAt from response msg {}, formatted Date {}", date1,locDate.format(date1));
    }
}