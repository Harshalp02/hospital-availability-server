package com.harshal.controller;

import com.harshal.modal.Doctor;
import com.harshal.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // Create a new doctor
    @PostMapping("/create")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
    	System.out.println("hoii  "+doctor);
        return doctorService.saveDoctor(doctor);
    }

    // Get all doctors
    @GetMapping("/view")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors(); // This will now use the custom query
    }

    // Get a doctor by ID
    @GetMapping("/{id}")
    public Optional<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // Update an existing doctor
    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        return doctorService.updateDoctor(id, doctorDetails);
    }

    // Delete a doctor
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}
