package com.harshal.controller;

import com.harshal.modal.Bed;
import com.harshal.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/beds")
public class BedController {

    @Autowired
    private BedService bedService;

    // Get all beds
    @GetMapping("/view")
    public List<Bed> getAllBeds() {
        return bedService.getAllBeds();
    }

    // Get bed by ID
    @GetMapping("/{id}")
    public Optional<Bed> getBedById(@PathVariable Long id) {
        return bedService.getBedById(id);
    }

    // Create a new bed
    @PostMapping("/create")
    public Bed createBed(@RequestBody Bed bed) {
    	System.out.println("bed"+bed);
        return bedService.saveBed(bed);
    }

    // Update an existing bed
    @PutMapping("/update/{id}")
    public Bed updateBed(@PathVariable Long id, @RequestBody Bed bed) {
        return bedService.updateBed(id, bed);
    }

    // Delete a bed
    @DeleteMapping("/delete/{id}")
    public String deleteBed(@PathVariable Long id) {
        bedService.deleteBed(id);
        return "Bed deleted successfully with ID: " + id;
    }
}
