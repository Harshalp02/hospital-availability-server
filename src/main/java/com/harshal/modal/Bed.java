package com.harshal.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bedNumber;

    private Boolean available;

    private Integer numberOfBeds; 

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
