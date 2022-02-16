package ru.job4j.passportapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.passportapi.model.Passport;
import ru.job4j.passportapi.service.PassportService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/passport")
@RestController
public class PassportController {

    private final PassportService passportService;

    @GetMapping("/find")
    public List<Passport> getAll(@RequestParam(value = "series", required = false) String series) {
        if (series != null && !"".equals(series)) {
            return passportService.findAllBySeries(series);
        }
        return passportService.findAll();
    }

    @GetMapping("/find/unavailable")
    public List<Passport> getAllUnavailable() {
        return passportService.findAllUnavailable();
    }

    @GetMapping("/find/replaceable")
    public List<Passport> getAllToBeReplaced() {
        return passportService.findAllToBeReplaced();
    }

    @PostMapping("/save")
    public Passport save(@Valid @RequestBody Passport passport) {
        return passportService.create(passport);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Passport> update(@PathVariable Integer id, @Valid @RequestBody Passport passport) {
        Passport passportFromDb = passportService.findById(id);
        if (passportFromDb == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(passportService.update(passportFromDb, passport), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        passportService.delete(id);
    }
}
