package nl.kristalsoftware.datastore.base.viewstore;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BaseCouchDBViewStore<T> {

    private final RestTemplate restTemplate;

    protected BaseCouchDBViewStore(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected T getDocument(String url, Class<T> clazz) {
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, clazz);
        return responseEntity.getBody();
    }

    protected void createOrUpdateDocument(String url, T document) {
        HttpEntity<T> httpEntity = new HttpEntity<>(document);
        restTemplate.put(url, httpEntity);
    }

    protected void deleteDocument(String url) {
        restTemplate.delete(url);
    }

}
