package ru.job4j.passportapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.passportapi.model.Passport;

import java.time.LocalDate;
import java.util.List;

public interface PassportRepository extends CrudRepository<Passport, Integer> {

    List<Passport> findAllBySeriesEquals(String series);

    List<Passport> findAllByExpirationDateGreaterThan(LocalDate date);

    List<Passport> findAllByExpirationDateLessThan(LocalDate date);
}
