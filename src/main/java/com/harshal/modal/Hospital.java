package com.harshal.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "hospital")
    @JsonIgnoreProperties("hospital") // Ignore the hospital field in Doctor during serialization
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "hospital")
    @JsonIgnoreProperties("hospital")
    private List<Bed> beds;

    @ManyToMany(mappedBy = "hospitals")
    @JsonIgnoreProperties("hospitals")
    private List<User> users;
}
