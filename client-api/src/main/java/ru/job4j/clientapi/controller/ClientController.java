package ru.job4j.clientapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.job4j.clientapi.dto.PassportDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClientController {

    private final RestTemplate restTemplate;

    @Value("${passport-url}")
    private String passportUrl;

    @GetMapping("/find")
    public List getAll(@RequestParam(value = "series", required = false) String series) {
        String uri = UriComponentsBuilder.fromUriString(passportUrl + "/find")
                .queryParam("series", series)
                .toUriString();
        return restTemplate.getForObject(uri, List.class);
    }

    @GetMapping("/find/unavailable")
    public List getAllUnavailable() {
        return restTemplate.getForObject(passportUrl + "/find/unavailable", List.class);
    }

    @GetMapping("/find/replaceable")
    public List getAllToBeReplaced() {
        return restTemplate.getForObject(passportUrl + "/find/replaceable", List.class);
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
