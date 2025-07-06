package br.com.musicfy.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiService {
  private final ObjectMapper mapper = new ObjectMapper();

  public ApiService() {
  }

  public String gerarResumo(String artista) {
    String token = "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";
    String jsonBody =
        """
            {
              "model": "meta-llama/llama-3.2-3b-instruct",
              "stream": false,
              "messages": [
                {
                  "role": "system",
                  "content": "Você é um assistente, que responde em português do Brasil, de forma resumida e objetiva sobre o(a) artista ou banda solicitado(a). Esse artista ou banda pode ser do mundo todo. Por se tratar de artista do ramo da música, quero que você insira nesse resumo as principais canções."
                },
                {
                  "role": "user",
                  "content": "Me dê um resumo breve e objetivo sobre o(a) artista ou a banda a seguir: %s"
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
