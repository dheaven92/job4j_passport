package ru.job4j.passportapi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Table(name = "passport")
@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Pattern(regexp = "^\\d{4}$", message = "Passport series must contain 4 digits")
    @Column(name = "series", nullable = false)
    private String series;

    @Pattern(regexp = "^\\d{6}$", message = "Passport number must contain 6 digits")
    @Column(name = "number", nullable = false)
    private String number;

    @NotNull(message = "Expiration date is required")
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
}
