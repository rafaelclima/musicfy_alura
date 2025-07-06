package br.com.musicfy.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiService {
  private final ObjectMapper mapper = new ObjectMapper();

  private String token = System.getenv("HF_TOKEN");

  public ApiService() {
  }

  public String gerarResumo(String artista) {

    if (token == null) {
      throw new RuntimeException("Token de autorização do Hugging Face não encontrado");
    }

    String jsonBody =
        """
            {
              "model": "meta-llama/llama-3-8b-instruct",
              "stream": false,
              "messages": [
                {
                  "role": "system",
                  "content": "Você é um assistente especializado em resumir informações sobre artistas ou bandas. Você só responde em português do Brasil, de forma resumida e objetiva sobre o(a) artista ou banda solicitado(a) pelo usuário. Por se tratar de artista do ramo da música, quero que você insira nesse resumo os principais trabalhos, álbuns e músicas do artista ou banda."
                },
                {
                  "role": "user",
                  "content": "Quero saber mais sobre %s"
                }
              ]
            }
            """
            .formatted(artista);

    HttpRequest request = HttpRequest.newBuilder()
        .uri(
            URI.create("https://router.huggingface.co/novita/v3/openai/chat/completions"))
        .header("Authorization", "Bearer " + token)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
        .build();

    HttpClient client = HttpClient.newHttpClient();
    try {
      var response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new RuntimeException("Erro: " + response.body());
      }

      JsonNode root = mapper.readTree(response.body());
      String textoGerado =
          root.get("choices").get(0).get("message").get("content").asText();
      return textoGerado.trim();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
