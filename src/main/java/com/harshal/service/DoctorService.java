package com.harshal.service;

import com.harshal.modal.Doctor;
import com.harshal.modal.Hospital;
import com.harshal.repository.DoctorRepository;
import com.harshal.repository.HospitalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    public Doctor saveDoctor(Doctor doctor) {
        if (doctor.getHospital() == null || doctor.getHospital().getId() == null) {
            throw new RuntimeException("Hospital must be specified for the doctor");
        }

        Hospital hospital = hospitalRepository.findById(doctor.getHospital().getId())
            .orElseThrow(() -> new RuntimeException("Hospital not found"));

        doctor.setHospital(hospital);
        return doctorRepository.save(doctor);
    }



    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        return doctorRepository.findById(id).map(doctor -> {
            doctor.setName(doctorDetails.getName());
            doctor.setSpecialty(doctorDetails.getSpecialty());
            doctor.setEmail(doctorDetails.getEmail());

            // Update the Hospital
            Hospital hospital = hospitalRepository.findById(doctorDetails.getHospital().getId())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));
            doctor.setHospital(hospital);

            return doctorRepository.save(doctor);
        }).orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}

