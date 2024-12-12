package com.harshal.service;

import com.harshal.modal.Hospital;
import com.harshal.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Optional<Hospital> getHospitalById(Long id) {
        return hospitalRepository.findById(id);
    }

    public Hospital updateHospital(Long id, Hospital hospitalDetails) {
        return hospitalRepository.findById(id).map(hospital -> {
            hospital.setName(hospitalDetails.getName());
            hospital.setAddress(hospitalDetails.getAddress());
            return hospitalRepository.save(hospital);
        }).orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
    }

    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);
    }
}
