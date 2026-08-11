package com.carteira.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL = "https://googleapis.com";

    public String processarAudio(byte[] audioBytes, String mimeType) throws Exception {
        String base64Audio = Base64.getEncoder().encodeToString(audioBytes);

        // Criando a instrução para a IA devolver um JSON limpo
        String prompt = "Analise o áudio desta despesa. Extraia o valor numérico, o local/descrição e classifique em uma categoria (Ex: ALIMENTACAO, TRANSPORTE, SAUDE, LAZER, OUTROS). Responda estritamente em formato JSON com as chaves: 'descricao', 'valor', 'categoria'. Não adicione formatação markdown.";

        JSONObject jsonRequestBody = new JSONObject()
            .put("contents", new org.json.JSONArray()
                .put(new JSONObject().put("parts", new org.json.JSONArray()
                    .put(new JSONObject().put("text", prompt))
                    .put(new JSONObject().put("inlineData", new JSONObject()
                        .put("mimeType", mimeType)
                        .put("data", base64Audio)
                    ))
                ))
            );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GEMINI_URL + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Extrai o texto puro retornado pelo Gemini
        JSONObject jsonResponse = new JSONObject(response.body());
        return jsonResponse.getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text").trim();
    }
}
