package com.harshal.repository;

import com.harshal.modal.Doctor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
//	@Query("SELECT d FROM Doctor d JOIN FETCH d.hospital")
//	List<Doctor> findAllDoctorsWithHospital();
	@Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.hospital")
	List<Doctor> findAllDoctorsWithHospital();

}
