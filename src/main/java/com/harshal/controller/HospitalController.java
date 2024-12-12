package com.harshal.controller;

import com.harshal.modal.Hospital;
import com.harshal.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // Get all hospitals
    @GetMapping("/view")
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    // Get a hospital by ID
    @GetMapping("/{id}")
    public Optional<Hospital> getHospitalById(@PathVariable Long id) {
        return hospitalService.getHospitalById(id);
    }

    // Create a new hospital
    @PostMapping("/create")
    public Hospital createHospital(@RequestBody Hospital hospital) {
        return hospitalService.saveHospital(hospital);
    }

    // Update an existing hospital
    @PutMapping("/{id}")
    public Hospital updateHospital(@PathVariable Long id, @RequestBody Hospital hospitalDetails) {
        return hospitalService.updateHospital(id, hospitalDetails);
    }

    // Delete a hospital
    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable Long id) {
        hospitalService.deleteHospital(id);
    }
}
