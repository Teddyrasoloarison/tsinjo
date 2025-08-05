package com.hei.school.model;

import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class VolaClient {

  private final RestTemplate restTemplate;
  private final String baseUrl;
  private final String apiKey;

  public VolaClient(RestTemplate restTemplate, String baseUrl, String apiKey) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
    this.apiKey = apiKey;
  }

  private HttpHeaders buildHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    // header d'API key — adapte le nom si la spec l'exige
    headers.set("x-api-key", apiKey);
    return headers;
  }

  /**
   * Soumet un paiement à Vola (POST). Retourne l'externalId renvoyé par Vola (ou null si erreur).
   * Le payload doit être un Map correspondant au JSON attendu par Vola.
   */
  public String submitPayment(Map<String, Object> payload) {
    String url = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/payments").toUriString();
    HttpEntity<Map<String, Object>> req = new HttpEntity<>(payload, buildHeaders());

    ResponseEntity<Map> resp = restTemplate.postForEntity(url, req, Map.class);
    if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
      Object id = resp.getBody().get("id"); // adapte la clé si besoin
      return id != null ? id.toString() : null;
    }
    return null;
  }

  /**
   * Récupère le paiement par externalId (GET /payments/{id}), retourne la réponse brute (Map) ou
   * null si échec.
   */
  @SuppressWarnings("unchecked")
  public Map<String, Object> getPaymentById(String externalId) {
    try {
      String url =
          UriComponentsBuilder.fromHttpUrl(baseUrl)
              .path("/payments/")
              .path(externalId)
              .toUriString();

      HttpEntity<Void> req = new HttpEntity<>(buildHeaders());
      ResponseEntity<Map> resp = restTemplate.exchange(url, HttpMethod.GET, req, Map.class);
      if (resp.getStatusCode().is2xxSuccessful()) {
        return (Map<String, Object>) resp.getBody();
      }
    } catch (Exception e) {
      // log si tu utilises un logger, par exemple: logger.warn("Vola get failed", e);
    }
    return null;
  }
}
