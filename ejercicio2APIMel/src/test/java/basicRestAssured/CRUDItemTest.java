package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CRUDItemTest {
    @Test
    public void verifyCRUDItem(){
        JSONObject body = new JSONObject();
        body.put("Content","Melissa");
        body.put("Icon",6);

        //create project
        Response response =given()
                .auth()
                .preemptive()
                .basic("melJimenez@ucb2022.com","12345")
                .body(body.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/projects.json");
        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("Melissa"))
                .body("Icon",equalTo(6));

        int idProject= response.then().extract().path("Id");

        //create item
        body = new JSONObject();
        body.put("Content","GoToTheVetForPetsVaccines");
        body.put("ProjectId",idProject);

        response =given()
                .auth()
                .preemptive()
                .basic("melJimenez@ucb2022.com","12345")
                .body(body.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/items.json");
        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("GoToTheVetForPetsVaccines"))
                .body("ProjectId",equalTo(idProject));

        int idItem= response.then().extract().path("Id");

        // read

        response =given()
                .auth()
                .preemptive()
                .basic("melJimenez@ucb2022.com","12345")
                .log().all()
                .when()
                .get("https://todo.ly/api/items/"+idItem+".json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("GoToTheVetForPetsVaccines"))
                .body("ProjectId",equalTo(idProject));

        // update

        body.put("Content","GoToTheVetForPetsVaccinesControl");
        response =given()
                .auth()
                .preemptive()
                .basic("melJimenez@ucb2022.com","12345")
                .body(body.toString())
                .log().all()
                .when()
                .put("https://todo.ly/api/items/"+idItem+".json");
        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("GoToTheVetForPetsVaccinesControl"))
                .body("ProjectId",equalTo(idProject));

        // delete
       response =given()
                .auth()
                .preemptive()
                .basic("melJimenez@ucb2022.com","12345")
                .log().all()
                .when()
                .delete("https://todo.ly/api/items/"+idItem+".json");
        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("GoToTheVetForPetsVaccinesControl"))
                .body("ProjectId",equalTo(idProject));

       /*given()
                .auth()
                .preemptive()
                .basic("melJimenez@ucb2022.com","12345")
                .body("{\n" +
                        "  \"Content\":\"newItemMel\",\n" +
                        "  \"ProjectId\":\"4025661\",\n" +
                        "}")
                .log().all()
                .when()
                .post("https://todo.ly/api/items.json")
                .then()
                .log().all();*/

        /*given()
                .auth()
                .preemptive()
                .basic("melJimenez@ucb2022.com","12345")
                /*.body("{\n" +
                        "  \"Content\":\"newItemMel\",\n" +
                        "}")
                .log().all()
                .when()
                .get("https://todo.ly/api/projects.xml")
                .then()
                .log().all();*/

    }
}
