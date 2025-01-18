package com.example.candid.Myql_POC;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoltireModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    String name;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Book cannot be null")
    String book;

    @NotNull(message = "age cannot be null")
    @Min(value = 1, message = "age must be greater than 0")
    @Max(value = 1000, message = "age must be less than or equal to 1000")
    Integer age;

}
