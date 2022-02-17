package ru.job4j.clientapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.job4j.clientapi.dto.PassportDto;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClientController {

    private final RestTemplate restTemplate;

    @Value("${passport-url}")
    private String passportUrl;

    @GetMapping("/find")
    public List<PassportDto> getAll(@RequestParam(value = "series", required = false) String series) {
        String uri = UriComponentsBuilder.fromUriString(passportUrl + "/find")
                .queryParam("series", series)
                .toUriString();
        List<PassportDto> body = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PassportDto>>() { }
        ).getBody();
        return body != null ? body : Collections.emptyList();
    }

    @GetMapping("/find/unavailable")
    public List<PassportDto> getAllUnavailable() {
        List<PassportDto> body = restTemplate.exchange(
                passportUrl + "/find/unavailable",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PassportDto>>() { }
        ).getBody();
        return body != null ? body : Collections.emptyList();
    }

    @GetMapping("/find/replaceable")
    public List<PassportDto> getAllToBeReplaced() {
        List<PassportDto> body = restTemplate.exchange(
                passportUrl + "/find/replaceable",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PassportDto>>() { }
        ).getBody();
        return body != null ? body : Collections.emptyList();
    }

    @PostMapping("/save")
    public PassportDto save(@RequestBody PassportDto passport) {
        ResponseEntity<PassportDto> response = restTemplate.postForEntity(passportUrl + "/save", passport, PassportDto.class);
        return response.getBody();
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Integer id, @RequestBody PassportDto passport) {
        restTemplate.put(passportUrl + "/update/" + id, passport, PassportDto.class);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        restTemplate.delete(passportUrl + "/delete/" + id);
    }
}
