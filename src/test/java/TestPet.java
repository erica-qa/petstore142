// 0 - Nome do pacote

// 1 - Bibliotecas

import io.restassured.response.Response; // Classe respota do REST-assured

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given; // Função given
import static org.hamcrest.Matchers.*; // Classe de verificadores do Hamcrest

// 2 - Classe
public class TestPet {
    // 2.1 Atributos

    static String ct = "application/json"; // Content type
    static String uriPet = "https://petstore.swagger.io/v2/pet"; // Base URL + endpoint

    // 2.2 Funções e Métodos
    // 2.2.1 Funções e Métodos Comuns / Úteis

    // Função de Leitura de json
    public static String lerArquivoJson(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    // 2.2.2 métodos de teste

    @Test
    public void testPostPet() throws IOException {
        // Configura
        // Carregar os dados do arquivo json do pet
        String jsonBody = lerArquivoJson("src/test/resources/json/pet1.json");
        int petId = 390933801; // código esperado do pet

        // começa o teste via REST-assured

        given() // Dado que
                .contentType(ct) // o tipo do conteúdo é
                .log().all() // mostre tudo na ida
                .body(jsonBody) // envie o corpo da requisição

                // Executa
                .when() // Quando
                .post(uriPet) // chamamos o endpoint fazendo post

                // Valida
                .then() // Então
                .log().all() // Mostre tudo na volta
                .statusCode(200) // Verifiquese o status code é 200 (comunicou com sucesso)
                .body("name", is("Snoopy")) // Verifica se o nome é Snoopy
                .body("id", is(petId)) // Verifica o código do pet
                .body("category.name", is("cachorro")) // Verifica se é cachorro
                .body("tags[0].name", is("vacinado")) // Verifica se está vacinado
        ;// fim do given
    }

}
