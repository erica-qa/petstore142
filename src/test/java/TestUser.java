
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

//import java.rmi.StubNotFoundException;

import static org.hamcrest.Matchers.hasLength;

import org.junit.jupiter.api.Test;

import io.restassured.response.Response;


public class TestUser {
    static String ct = "application/json";
    static String uriUser = "https://petstore.swagger.io/v2/user";
    static String token;


    @Test
public static String testLogin(){
    // Configura
    String username = "charlie";
    String password = "abcdef";

    String resultadoEsperado = "logged in user session:";
    Response resposta = (Response) given()
        .contentType(ct)
        .log().all()
    // Executa
    .when()
        .get(uriUser + "/login?username="+ username + "&password=" + password)
    // Valida
    .then()
        .log().all()
        .statusCode(200)
        .body("code", is(200))
        .body("type", is("unknown"))
        .body("message", containsString(resultadoEsperado))
        .body("message", hasLength(36))
    .extract();
    ;

    //Extração
    String token = resposta.jsonPath().getString("message").substring(23);
    System.out.println("Conteúdo do Token:" + token);
    return token;
}

}
