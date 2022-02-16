package ru.job4j.passportapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.passportapi.model.Passport;
import ru.job4j.passportapi.repository.PassportRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PassportService {

    private final PassportRepository passportRepository;

    public List<Passport> findAll() {
        return (List<Passport>) passportRepository.findAll();
    }

    public List<Passport> findAllBySeries(String series) {
        return passportRepository.findAllBySeriesEquals(series);
    }

    public List<Passport> findAllUnavailable() {
        return passportRepository.findAllByExpirationDateLessThan(LocalDate.now());
    }

    public List<Passport> findAllToBeReplaced() {
        List<Passport> passports = passportRepository.findAllByExpirationDateGreaterThan(LocalDate.now());
        return passports.stream()
                .filter(passport -> LocalDate.now().isAfter(passport.getExpirationDate().minusMonths(3)))
                .collect(Collectors.toList());
    }

    public Passport create(Passport passport) {
        return passportRepository.save(passport);
    }

    public Passport update(Passport originalPassport, Passport updatedPassport) {
        originalPassport.setSeries(updatedPassport.getSeries());
        originalPassport.setNumber(updatedPassport.getNumber());
        originalPassport.setExpirationDate(updatedPassport.getExpirationDate());
        return passportRepository.save(originalPassport);
    }

    public void delete(int id) {
        passportRepository.deleteById(id);
    }

    public Passport findById(int id) {
        return passportRepository.findById(id).orElse(null);
    }
}
