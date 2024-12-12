package com.harshal.service;

import com.harshal.modal.Bed;
import com.harshal.repository.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BedService {

    @Autowired
    private BedRepository bedRepository;

    public List<Bed> getAllBeds() {
        return bedRepository.findAll();
    }

    public Optional<Bed> getBedById(Long id) {
        return bedRepository.findById(id);
    }

    public Bed saveBed(Bed bed) {
        return bedRepository.save(bed);
    }

    public Bed updateBed(Long id, Bed updatedBed) {
        return bedRepository.findById(id).map(existingBed -> {
            existingBed.setBedNumber(updatedBed.getBedNumber());
            existingBed.setAvailable(updatedBed.getAvailable());
            existingBed.setNumberOfBeds(updatedBed.getNumberOfBeds());
            existingBed.setHospital(updatedBed.getHospital());
            return bedRepository.save(existingBed);
        }).orElseThrow(() -> new RuntimeException("Bed not found with id: " + id));
    }


    public void deleteBed(Long id) {
        bedRepository.deleteById(id);
    }
}
